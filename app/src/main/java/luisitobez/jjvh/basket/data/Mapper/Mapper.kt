package luisitobez.jjvh.basket.data.Mapper

import luisitobez.jjvh.basket.data.local.entity.GameEntity
import luisitobez.jjvh.basket.domain.model.gameModel

fun GameEntity.toDomain(): gameModel {
    return gameModel(
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

fun gameModel.toEntity(): GameEntity {
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