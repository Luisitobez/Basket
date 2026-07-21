package luisitobez.jjvh.basket.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView(
    """SELECT g.id AS game_id,
        COALESCE(SUM(CASE WHEN e.team_id = g.home_team_id AND e.is_cancelled = 0 THEN e.points ELSE 0 END), 0) AS home_score,
        COALESCE(SUM(CASE WHEN e.team_id = g.away_team_id AND e.is_cancelled = 0 THEN e.points ELSE 0 END), 0) AS away_score
       FROM game g LEFT JOIN game_event e ON e.game_id = g.id GROUP BY g.id""",
    viewName = "v_game_score"
)
data class GameScoreView(
    @ColumnInfo(name = "game_id") val gameId: Long,
    @ColumnInfo(name = "home_score") val homeScore: Int,
    @ColumnInfo(name = "away_score") val awayScore: Int
)