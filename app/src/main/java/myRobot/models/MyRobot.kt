package myRobot.models

import myRobot.models.hands.Hand
import myRobot.models.heads.Head
import model.legs.Leg

class MyRobot(private val head: Head, private val hand: Hand, private val leg: Leg) : IRobot {
    override fun action() {
        head.speak()
        hand.upHand()
        leg.makeStep()
    }

    override fun getPrice(): Int {
        return head.getPrice() + hand.getPrice() + leg.getPrice()
    }
}