package model.hands.brands

import myRobot.models.hands.Hand

class ToshibaHand(price: Int) : Hand(price) {
    override fun upHand() {
        println("Toshiba hands up")
    }
}