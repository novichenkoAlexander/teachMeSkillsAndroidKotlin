package model.heads.brands

import myRobot.models.heads.Head

class SonyHead(price: Int) : Head(price) {
    override fun speak() {
        println("Sony's head is speaking")
    }
}