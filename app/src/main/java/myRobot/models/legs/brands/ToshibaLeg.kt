package model.legs.brands

import model.legs.Leg

class ToshibaLeg(price: Int) : Leg(price) {
    override fun makeStep() {
        println("Toshiba is making step")
    }
}