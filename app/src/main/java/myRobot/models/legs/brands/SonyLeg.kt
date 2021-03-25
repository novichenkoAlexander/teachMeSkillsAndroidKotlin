package myRobot.models.legs.brands

import model.legs.Leg

class SonyLeg(price: Int) : Leg(price) {
    override fun makeStep() {
        println("Sony's leg is making step")
    }
}