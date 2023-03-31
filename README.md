# ChicagoBulls
Basketball game simulation in console
<p align="center">
  <img src="https://github.com/SzDudek/ChicagoBulls/blob/master/Bulls.png" />
    <br/> 
</p>

## Brief description
The simulation is to represent a basketball game of two teams consisting of 3 players. Each of them has the following characteristics:
- speed - the priority in making a move, the players are sorted in a table from the fastest to the slowest, and in that order, they make their moves in a turn
- throwing accuracy - the percentage chance of hitting the basket after the shot is made
- defense - the percentage chance of taking the ball away from the opponent 
- passing accuracy - the percentage chance to make an accurate pass to the nearest ally

In addition, you can choose one of four types of players:
- Beanpole - a typical tall basketball player, in our simulation his characteristic feature will be the ability to make a Dunk, i.e. make a "throw" from very close range with a 100% chance of hitting.
- Big guy - a defensive player, his special skill is the Wall, temporarily he has a one hundred percent chance to take the ball away from the opponent on the adjacent field.
- Speedie - agile player, whose special skill is the ability to make two moves in one turn.
- Custom - a player with no special skills, you can freely modify his characteristics.

The players move around the field, which is a two-dimensional board displayed in the console. The players of the first team are represented by the number 1, while the second team is represented by the number 2. When the ball is not in anyone's possession, it is represented by the number 3 highlighted in red. In the situation when any player holds it in his hands his number is highlighted, so it is easy to follow the current position of the ball on the field.

The goal of both teams is to score as many points as possible. To achieve this, the players try to seize the ball, and when they succeed, they must run to one of the designated fields and make a throw (depending on the field chosen for three or two points). If they score, the ball stays on the field where the basket is located and the game is resumed by the team that lost the points. Otherwise, the ball bounces and goes to the team whose players manage to collect the ball. The result of the match is displayed in real-time in the console and saved in a text file, which additionally contains statistics such as the number of shots hit or interceptions, among others.
