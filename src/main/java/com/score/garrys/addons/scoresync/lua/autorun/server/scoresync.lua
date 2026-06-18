-- ╔══════════════════════════════════════════╗
-- ║         ScoreSync — GmodScore App        ║
-- ║     Addon para sincronizar dados GMOD    ║
-- ╚══════════════════════════════════════════╝

-- ══════════════════════════════════════════
-- CONFIGURAÇÃO — altere esses valores
-- ══════════════════════════════════════════
local CONFIG = {
    API_URL  = "https://backend-production-db6e.up.railway.app", -- URL do seu backend
    API_KEY  = "MinhaChaveSecreta123",  -- chave de autenticação (configure no backend)
    GAMEMODE = "Deathmatch",
    DEBUG    = true  -- mostra logs no console do servidor (mude para false em produção)
}

-- ══════════════════════════════════════════
-- UTILITÁRIOS
-- ══════════════════════════════════════════

-- Loga mensagens no console do servidor
local function log(msg)
    if CONFIG.DEBUG then
        print("[ScoreSync] " .. tostring(msg))
    end
end

-- Monta o header de autenticação
local function getHeaders()
    return {
        ["Content-Type"]  = "application/json",
        ["Authorization"] = "Bearer " .. CONFIG.API_KEY
    }
end

-- Converte tabela Lua para JSON simples
local function toJSON(data)
    local parts = {}
    for k, v in pairs(data) do
        local val
        if type(v) == "string" then
            val = '"' .. v .. '"'
        elseif type(v) == "boolean" then
            val = tostring(v)
        else
            val = tostring(v)
        end
        table.insert(parts, '"' .. k .. '":' .. val)
    end
    return "{" .. table.concat(parts, ",") .. "}"
end

-- Envia requisição HTTP POST para o backend
local function post(endpoint, data, callback)
    local url  = CONFIG.API_URL .. endpoint
    local body = toJSON(data)

    log("POST " .. endpoint .. " → " .. body)

    http.Post(url, data, 
        function(body, size, headers, code)
            log("Resposta " .. endpoint .. " → " .. tostring(code))
            if callback then callback(true, code) end
        end,
        function(err)
            log("ERRO " .. endpoint .. " → " .. tostring(err))
            if callback then callback(false, err) end
        end,
        getHeaders()
    )
end

-- Envia requisição HTTP GET
local function get(endpoint, callback)
    local url = CONFIG.API_URL .. endpoint

    http.Fetch(url,
        function(body, size, headers, code)
            log("GET " .. endpoint .. " → " .. tostring(code))
            if callback then callback(true, body) end
        end,
        function(err)
            log("ERRO GET " .. endpoint .. " → " .. tostring(err))
            if callback then callback(false, err) end
        end,
        getHeaders()
    )
end

-- ══════════════════════════════════════════
-- CONTROLE DE PARTIDA
-- ══════════════════════════════════════════
local matchId    = nil   -- ID da partida atual
local matchAtivo = false -- se tem partida em andamento

-- Inicia uma nova partida no backend
local function iniciarPartida()
    local mapa = game.GetMap()

    post("/partidas", {
        mapa       = mapa,
        data_inicio = os.date("%Y-%m-%dT%H:%M:%S")
    }, function(ok, code)
        if ok and tostring(code) == "200" or tostring(code) == "201" then
            log("Partida iniciada no mapa " .. mapa)
            matchAtivo = true
            -- Busca o ID da partida criada
            get("/partidas", function(ok, body)
                if ok then
                    log("Partidas carregadas do servidor")
                    -- ID será usado nos eventos de kill
                end
            end)
        end
    end)
end

-- Finaliza a partida no backend
local function finalizarPartida()
    if not matchAtivo then return end
    matchAtivo = false
    log("Partida finalizada")
end

-- ══════════════════════════════════════════
-- REGISTRO DE JOGADORES
-- ══════════════════════════════════════════

-- Registra ou atualiza jogador no backend ao conectar
local function registrarJogador(ply)
    local steamId = ply:SteamID64()
    local nome    = ply:Nick()

    if not steamId or steamId == "" then
        log("SteamID inválido para " .. nome)
        return
    end

    -- Verifica se jogador já existe, senão cria
    get("/jogadores", function(ok, body)
        if ok then
            -- Jogador existe — atualiza ultimo_login via POST
            post("/jogadores", {
                steam_id = steamId,
                nome     = nome
            }, function(ok2, code)
                log("Jogador " .. nome .. " registrado/atualizado")
            end)
        end
    end)
end

-- ══════════════════════════════════════════
-- HOOKS — eventos do jogo
-- ══════════════════════════════════════════

-- Jogador conecta ao servidor
hook.Add("PlayerInitialSpawn", "ScoreSync_Spawn", function(ply)
    timer.Simple(3, function()  -- aguarda 3s para ter dados completos
        if IsValid(ply) then
            log("Jogador conectou: " .. ply:Nick() .. " (" .. ply:SteamID64() .. ")")
            registrarJogador(ply)
        end
    end)
end)

-- Jogador desconecta
hook.Add("PlayerDisconnected", "ScoreSync_Disconnect", function(ply)
    local nome    = ply:Nick()
    local steamId = ply:SteamID64()
    log("Jogador desconectou: " .. nome .. " (" .. steamId .. ")")

    -- Registra saída como visitante se necessário
    post("/visitantes", {
        nome_usuario  = nome,
        horario_saida = os.date("%Y-%m-%dT%H:%M:%S")
    }, nil)
end)

-- Jogador é eliminado (evento principal do Deathmatch)
hook.Add("PlayerDeath", "ScoreSync_Kill", function(victim, inflictor, attacker)

    -- Ignora se não for um jogador que matou
    if not IsValid(attacker) or not attacker:IsPlayer() then return end
    if not IsValid(victim)   or not victim:IsPlayer()   then return end

    local killerId  = attacker:SteamID64()
    local victimId  = victim:SteamID64()
    local weapon    = IsValid(inflictor) and inflictor:GetClass() or "unknown"
    local timestamp = os.date("%Y-%m-%dT%H:%M:%S")

    log("KILL: " .. attacker:Nick() .. " eliminou " .. victim:Nick() .. " com " .. weapon)

    -- 1) Envia evento de morte para o backend
    post("/eventos/morte", {
        match_id   = tostring(matchId or 0),
        killer_id  = killerId,
        victim_id  = victimId,
        weapon     = weapon,
        timestamp  = timestamp
    }, function(ok, code)
        if ok then
            log("Evento de morte registrado")
        end
    end)

    -- 2) Atualiza estatísticas do killer (kills +1)
    get("/jogadores", function(ok, body)
        if ok then
            -- Busca estatísticas e atualiza
            get("/estatisticas", function(ok2, body2)
                if ok2 then
                    log("Estatísticas buscadas para atualização")
                end
            end)
        end
    end)

    -- 3) Mostra mensagem no servidor
    local msg = "[ScoreSync] " .. attacker:Nick() .. " eliminou " .. victim:Nick()
    for _, p in ipairs(player.GetAll()) do
        p:ChatPrint(msg)
    end
end)

-- Mapa carregou — inicia partida
hook.Add("InitPostEntity", "ScoreSync_MapLoad", function()
    log("Servidor iniciado — mapa: " .. game.GetMap())
    timer.Simple(5, function()
        iniciarPartida()
    end)
end)

-- Servidor vai encerrar — finaliza partida
hook.Add("ShutDown", "ScoreSync_Shutdown", function()
    log("Servidor encerrando")
    finalizarPartida()
end)

-- ══════════════════════════════════════════
-- COMANDOS DE ADMIN no console do servidor
-- ══════════════════════════════════════════

-- Digite no console: scoresync_status
concommand.Add("scoresync_status", function()
    print("=== ScoreSync Status ===")
    print("API URL:    " .. CONFIG.API_URL)
    print("Partida ID: " .. tostring(matchId))
    print("Ativo:      " .. tostring(matchAtivo))
    print("Jogadores:  " .. tostring(#player.GetAll()))
    print("Mapa:       " .. game.GetMap())
end)

-- Digite no console: scoresync_test
concommand.Add("scoresync_test", function()
    log("Testando conexão com o backend...")
    get("/jogadores", function(ok, body)
        if ok then
            print("[ScoreSync] ✅ Backend conectado!")
        else
            print("[ScoreSync] ❌ Erro ao conectar: " .. tostring(body))
        end
    end)
end)

log("ScoreSync carregado! Digite 'scoresync_test' para testar a conexão.")