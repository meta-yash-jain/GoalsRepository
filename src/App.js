import React, { Component } from 'react';
import './App.css';

const PLAYER_ONE_SYMBOL = "X"
const PLAYER_TWO_SYMBOL = "0"
class App extends Component {

  constructor(props) {
    super(props)
    this.state = {
      currentTurn: "X",
      board: [
        "", "", "", "", "", "", "", "", ""
      ],
      winner: null
    }
  }

  handleClick(index) {
    const { board, currentTurn, winner } = this.state
    if (board[index] === "" && !winner) {
      board[index] = currentTurn
      this.setState({
        board,
        currentTurn: currentTurn === PLAYER_ONE_SYMBOL ? PLAYER_TWO_SYMBOL : PLAYER_ONE_SYMBOL,
        winner: this.checkForWinner()
      })
    }
  }

  checkForWinner() {
    const { board, currentTurn } = this.state

    var winningCombos = [[0, 1, 2], [3, 4, 5], [6, 7, 8], [0, 3, 6], [1, 4, 7], [2, 5, 8], [0, 4, 8], [2, 4, 6]]
    return winningCombos.find(function (combo) {
      if (board[combo[0]] !== "" && board[combo[1]] !== "" && board[combo[2]] !== "" && board[combo[0]] === board[combo[1]] && board[combo[1]] === board[combo[2]]) {
        return currentTurn
      } else {
        return false
      }
    })
  }

  render() {
    const { board, winner } = this.state
    const tieGame = [].concat(board).sort().reverse().pop() !== ""  && !winner

    return (
      <div className="app-container">
        <h1>React Tic Tac Toe Game</h1>
        <div className="board">
          {board.map((cell, index) => {
            return <div key={index} onClick={() => this.handleClick(index)} className="square">{cell}</div>
          })}
        </div>
        {winner && alert(`The final winner: ${board[winner[0]]}`)}
        {tieGame && alert('It\'s a tie game')}
      </div>
    )
  }
}

export default App;