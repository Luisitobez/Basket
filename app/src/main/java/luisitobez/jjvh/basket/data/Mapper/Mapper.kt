package luisitobez.jjvh.basket.data.Mapper

import luisitobez.jjvh.basket.data.local.dao.TeamDao
import luisitobez.jjvh.basket.data.local.entity.GameEntity
import luisitobez.jjvh.basket.data.local.entity.TeamEntity
import luisitobez.jjvh.basket.domain.model.GameModel
import luisitobez.jjvh.basket.domain.model.TeamModel

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