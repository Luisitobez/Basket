package luisitobez.jjvh.basket.data.Mapper

import luisitobez.jjvh.basket.data.local.dao.PlayerGameStats
import luisitobez.jjvh.basket.data.local.dao.TeamDao
import luisitobez.jjvh.basket.data.local.entity.GameEntity
import luisitobez.jjvh.basket.data.local.entity.CompetitionEntity
import luisitobez.jjvh.basket.data.local.entity.GameEventEntity
import luisitobez.jjvh.basket.data.local.entity.GameRosterEntity
import luisitobez.jjvh.basket.data.local.entity.GameScoreView
import luisitobez.jjvh.basket.data.local.entity.GameStaffEntity
import luisitobez.jjvh.basket.data.local.entity.TeamEntity
import luisitobez.jjvh.basket.data.local.entity.TeamPeriodFoulEntity
import luisitobez.jjvh.basket.domain.model.CompetitionModel
import luisitobez.jjvh.basket.domain.model.GameEventModel
import luisitobez.jjvh.basket.domain.model.GameModel
import luisitobez.jjvh.basket.domain.model.GameRosterModel
import luisitobez.jjvh.basket.domain.model.GameScoreModel
import luisitobez.jjvh.basket.domain.model.GameStaffModel
import luisitobez.jjvh.basket.domain.model.PlayerGameStatsModel
import luisitobez.jjvh.basket.domain.model.TeamModel
import luisitobez.jjvh.basket.domain.model.TeamPeriodFoulModel

fun GameEntity.toDomain(): GameModel {
    return GameModel(
        id = id,
        competitionId = competitionId,
        homeTeamId = homeTeamId,
        awayTeamId = awayTeamId,
        venue = venue,
        gameDate = gameDate,
        status = status,
        currentPeriod = currentPeriod,
        clockSecondsRemaining = clockSecondsRemaining,
        clockStartedAtEpochMs = clockStartedAtEpochMs,
        notes = notes
    )
}

fun GameModel.toEntity(): GameEntity {
    return GameEntity(
        id = id,
        competitionId = competitionId,
        homeTeamId = homeTeamId,
        awayTeamId = awayTeamId,
        venue = venue,
        gameDate = gameDate,
        status = status,
        currentPeriod = currentPeriod,
        clockSecondsRemaining = clockSecondsRemaining,
        clockStartedAtEpochMs = clockStartedAtEpochMs,
        notes = notes
    )
}

fun TeamEntity.toDomain(): TeamModel {
    return TeamModel(
        id = id,
        name = name,
        shortName = shortName,
        logoUri = logoUri
    )
}

fun TeamModel.toEntity(): TeamEntity {
    return TeamEntity(
        id = id,
        name = name,
        shortName = shortName,
        logoUri = logoUri
    )
}

fun GameRosterEntity.toDomain(): GameRosterModel {
    return GameRosterModel(
        id = id,
        gameId = gameId,
        teamId = teamId,
        playerName = playerName,
        jerseyNumber = jerseyNumber,
        isStarter = isStarter,
        isCaptain = isCaptain,
        isActive = isActive
    )
}

fun GameRosterModel.toEntity(): GameRosterEntity{
    return GameRosterEntity(
        id = id,
        gameId = gameId,
        teamId = teamId,
        playerName = playerName,
        jerseyNumber = jerseyNumber,
        isStarter = isStarter,
        isCaptain = isCaptain,
        isActive = isActive
    )
}

fun GameEventModel.toEntity(): GameEventEntity{
    return GameEventEntity(
        id = id,
        gameId = gameId,
        sequenceNumber = sequenceNumber,
        periodNumber = periodNumber,
        clockSecondsRemaining = clockSecondsRemaining,
        eventType = eventType,
        teamId = teamId,
        rosterId = rosterId,
        relatedRosterId = relatedRosterId,
        points = points,
        foulType = foulType,
        isCancelled = isCancelled,
        note = note,
        createdAt = createdAt
    )
}

fun GameEventEntity.toDomain(): GameEventModel{
    return GameEventModel(
        id = id,
        gameId = gameId,
        sequenceNumber = sequenceNumber,
        periodNumber = periodNumber,
        clockSecondsRemaining = clockSecondsRemaining,
        eventType = eventType,
        teamId = teamId,
        rosterId = rosterId,
        relatedRosterId = relatedRosterId,
        points = points,
        foulType = foulType,
        isCancelled = isCancelled,
        note = note,
        createdAt = createdAt
    )
}

fun GameScoreView.toDomain(): GameScoreModel {
    return GameScoreModel(
        gameId = gameId,
        homeTeamScore = homeScore,
        awayTeamScore = awayScore
    )
}

fun PlayerGameStatsModel.toEntity(): PlayerGameStats {
    return PlayerGameStats(
        gameId = gameId,
        rosterId = rosterId,
        teamId = teamId,
        playerName = playerName,
        jerseyNumber = jerseyNumber,
        points = points,
        fouls = fouls
    )
}

fun PlayerGameStats.toDomain(): PlayerGameStatsModel {
    return PlayerGameStatsModel(
        gameId = gameId,
        rosterId = rosterId,
        teamId = teamId,
        playerName = playerName,
        jerseyNumber = jerseyNumber,
        points = points,
        fouls = fouls
    )
}

fun CompetitionEntity.toDomain(): CompetitionModel = CompetitionModel(
    id = id,
    name = name,
    category = category,
    ruleset = ruleset,
    periodsCount = periodsCount,
    periodMinutes = periodMinutes,
    overtimeMinutes = overtimeMinutes
)

fun CompetitionModel.toEntity(): CompetitionEntity = CompetitionEntity(
    id = id,
    name = name,
    category = category,
    ruleset = ruleset,
    periodsCount = periodsCount,
    periodMinutes = periodMinutes,
    overtimeMinutes = overtimeMinutes
)

fun GameStaffEntity.toDomain(): GameStaffModel = GameStaffModel(
    id = id,
    gameId = gameId,
    teamId = teamId,
    role = role,
    fullName = fullName
)

fun GameStaffModel.toEntity(): GameStaffEntity = GameStaffEntity(
    id = id,
    gameId = gameId,
    teamId = teamId,
    role = role,
    fullName = fullName
)

fun TeamPeriodFoulEntity.toDomain(): TeamPeriodFoulModel = TeamPeriodFoulModel(
    gameId = gameId,
    teamId = teamId,
    periodNumber = periodNumber,
    foulCount = foulCount
)

fun TeamPeriodFoulModel.toEntity(): TeamPeriodFoulEntity = TeamPeriodFoulEntity(
    gameId = gameId,
    teamId = teamId,
    periodNumber = periodNumber,
    foulCount = foulCount
)
