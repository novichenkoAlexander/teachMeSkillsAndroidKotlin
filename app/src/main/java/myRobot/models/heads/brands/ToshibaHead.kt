package myRobot.models.heads.brands

import myRobot.models.heads.Head

class ToshibaHead(price: Int) : Head(price) {
    override fun speak() {
        println("Toshiba's head is speaking")
    }
}