package model.hands.brands

import myRobot.models.hands.Hand

class SamsungHand(price: Int) : Hand(price) {
    override fun upHand() {
        println("Samsung hands up")
    }
}