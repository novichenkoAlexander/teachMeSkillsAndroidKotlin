package myRobot.models.hands

import model.hands.IHand

abstract class Hand(private val price: Int) : IHand {
    override fun getPrice(): Int {
        return price
    }

}