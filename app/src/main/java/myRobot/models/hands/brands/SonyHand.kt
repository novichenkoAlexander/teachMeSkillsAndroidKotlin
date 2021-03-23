package myRobot.models.hands.brands

import myRobot.models.hands.Hand

class SonyHand(price: Int) : Hand(price) {
    override fun upHand() {
        println("Sony hand is up")
    }
}