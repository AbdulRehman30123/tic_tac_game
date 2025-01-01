package com.rehmankhan.tictactoegame.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rehmankhan.tictactoegame.R
import com.rehmankhan.tictactoegame.logic.BackEnd
import com.rehmankhan.tictactoegame.ui.theme.AirSquareColor
import com.rehmankhan.tictactoegame.ui.theme.BoardSquareColor
import com.rehmankhan.tictactoegame.ui.theme.CustomShapes
import com.rehmankhan.tictactoegame.ui.theme.DrawSquareColor
import com.rehmankhan.tictactoegame.ui.theme.PlayerSquareColor


@Composable


fun MainUi() {
    val gameBackEnd = BackEnd()

    var currentMove by remember { gameBackEnd.currentTurn }
    var moves = remember { gameBackEnd.moves }
    var playerScore by remember { gameBackEnd.playerScore }
    var aiScore by remember { gameBackEnd.aiScore }
    var drawScore by remember { gameBackEnd.drawScore }

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .fillMaxSize()
    ) {

        // now we need a show to display the result of
        // player won draw and ai won

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ScoreCardWidget("Player 1", playerScore, PlayerSquareColor)
            ScoreCardWidget("Draw", drawScore, DrawSquareColor)
            ScoreCardWidget("Player 2", aiScore, AirSquareColor)

        }

        Box(
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .clip(CustomShapes().roundBorder10)
                .background(
                    if (currentMove == "player") {
                        PlayerSquareColor
                    } else {
                        AirSquareColor
                    }
                )
                .height(50.dp)

                .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {

            if (currentMove == "player") {
                Text(
                    text = "Player One Turn", color = Color.Black
                )
            } else {
                Text(text = "Player Two Turn", color = Color.Black)
            }
        }
        val localContext = LocalContext.current


        // now we need a board to show tic tac moves between player and ai
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            for (i in 0..2) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(1f)

                ) {
                    for (j in 0..2) {
                        Column {
                            BoardSquareWidget(

                                move = moves[3 * i + j],
                                onClick = {
                                    /*
                                    if player move active convert it to ai
                                    and vice versa
                                     */

                                    /*
                                    now make move
                                    if current move is of player and the value in move list is null then mark tic
                                    if player move is ai and the value in move list is null then mark cross
                                     */
                                    if (currentMove == "player" && moves[3 * i + j] == null) {
                                        // mark tick
                                        moves[3 * i + j] = true
                                        currentMove = "ai"
                                        val gameStatus: String = checkGameWon(
                                            allMoves = moves,
                                        )
                                        when (gameStatus) {
                                            "one won" -> {
                                                playerScore += 1
                                                for (i in 0..8) {
                                                    moves[i] = null
                                                }
                                                Toast.makeText(
                                                    localContext,
                                                    "player one won",
                                                    Toast.LENGTH_SHORT
                                                ).show();
                                            }

                                            "two won" -> {
                                                aiScore += 1

                                                Toast.makeText(
                                                    localContext,
                                                    "player two won",
                                                    Toast.LENGTH_SHORT
                                                ).show();
                                                for (i in 0..8) {
                                                    moves[i] = null
                                                }
                                            }

                                            "game is running" -> {

                                            }

                                            else -> {

                                                drawScore += 1

                                                Toast.makeText(
                                                    localContext,
                                                    "game draw",
                                                    Toast.LENGTH_SHORT
                                                ).show();
                                                for (i in 0..8) {
                                                    moves[i] = null
                                                }
                                            }
                                        }


//
                                    } else if (currentMove == "ai" && moves[3 * i + j] == null) {
                                        // mark cross
                                        moves[3 * i + j] = false
                                        currentMove = "player"
                                        val gameStatus: String = checkGameWon(
                                            allMoves = moves,
                                        )
                                        when (gameStatus) {
                                            "one won" -> {
                                                playerScore += 1
                                                for (i in 0..8) {
                                                    moves[i] = null
                                                }
                                                Toast.makeText(
                                                    localContext,
                                                    "player one won",
                                                    Toast.LENGTH_SHORT
                                                ).show();
                                            }

                                            "two won" -> {
                                                aiScore += 1
                                                for (i in 0..8) {
                                                    moves[i] = null
                                                }
                                                Toast.makeText(
                                                    localContext,
                                                    "player two won",
                                                    Toast.LENGTH_SHORT
                                                ).show();
                                            }

                                            "game is running" -> {

                                            }

                                            else -> {
                                                drawScore += 1
                                                for (i in 0..8) {
                                                    moves[i] = null
                                                }
                                                Toast.makeText(
                                                    localContext,
                                                    "game draw",
                                                    Toast.LENGTH_SHORT
                                                ).show();
                                            }
                                        }
//                                        Toast.makeText(
//                                            localContext,
//                                            "${moves[3 * i + j]}",
//                                            Toast.LENGTH_SHORT
//                                        ).show();
                                    } else {
                                        // show toast this block is already occupied
                                        Toast.makeText(
                                            localContext,
                                            "This block is already occupied",
                                            Toast.LENGTH_SHORT
                                        ).show();
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }


    }

}

@Composable
fun ScoreCardWidget(
    playerType: String,
    playerScore: Int,
    boxColor: Color
) {
    return Column(
        modifier = Modifier
            .clip(CustomShapes().roundBorder10)
            .background(color = boxColor)

            .height(100.dp)
            .width(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = playerType, color = Color.Black
        )
//        Spacer(modifier = Modifier.height(10.dp))
        Text(text = playerScore.toString(), color = Color.Black)
    }
}

// board square widget
@Composable
fun BoardSquareWidget(move: Boolean?, onClick: () -> Unit) {

    return Box(
        modifier = Modifier
            .clip(CustomShapes().roundBorder10)
            .background(color = BoardSquareColor)
            .height(100.dp)
            .width(100.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {

        // if boolean true show tic icon
        // if boolean false show cross icon
        // if null then show nothing
        GetIcon(move = move)
    }
}

@Composable
fun GetIcon(move: Boolean?) {
    return when (move) {
        true -> {

            Image(
                painter = painterResource(R.drawable.ticicon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
            )
        }

        false -> {
            Image(
                painter = painterResource(R.drawable.crossicon),
                contentDescription = null, contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
            )
        }

        null -> {
            Image(
                painter = painterResource(R.drawable.ic_null),
                contentDescription = null, contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
            )

        }
    }
}

fun checkGameWon(
    allMoves: List<Boolean?>,

    ): String {
    if (allMoves[0] == true && allMoves[1] == true && allMoves[2] == true ||
        allMoves[3] == true && allMoves[4] == true && allMoves[5] == true ||
        allMoves[6] == true && allMoves[7] == true && allMoves[8] == true ||
        allMoves[0] == true && allMoves[3] == true && allMoves[6] == true ||
        allMoves[1] == true && allMoves[4] == true && allMoves[7] == true ||
        allMoves[2] == true && allMoves[5] == true && allMoves[8] == true ||
        allMoves[0] == true && allMoves[4] == true && allMoves[8] == true ||
        allMoves[2] == true && allMoves[4] == true && allMoves[6] == true

    ) {


        return "one won"
    } else if (
        allMoves[0] == false && allMoves[1] == false && allMoves[2] == false ||
        allMoves[3] == false && allMoves[4] == false && allMoves[5] == false ||
        allMoves[6] == false && allMoves[7] == false && allMoves[8] == false ||
        allMoves[0] == false && allMoves[3] == false && allMoves[6] == false ||
        allMoves[1] == false && allMoves[4] == false && allMoves[7] == false ||
        allMoves[2] == false && allMoves[5] == false && allMoves[8] == false ||
        allMoves[0] == false && allMoves[4] == false && allMoves[8] == false ||
        allMoves[2] == false && allMoves[4] == false && allMoves[6] == false
    ) {


        return "two won"
    } else if (allMoves.contains(null)) {


        return "game is running"
    } else {

        return "draw"
    }
}

@Preview
@Composable
fun MainUiPreview() {
    MainUi()
}