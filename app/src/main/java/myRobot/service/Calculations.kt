package myRobot.service

import myRobot.models.MyRobot

object Calculations {
    fun getMostExpensiveRobot(robots: Array<MyRobot>): MyRobot {
        var maxRobotPrice = 0
        val mostExpensiveRobot = robots[maxRobotPrice]
        for (robot in robots) {
            val currentPrice = robot.getPrice()
            if (currentPrice >= maxRobotPrice) {
                maxRobotPrice = currentPrice
            }
        }
        return mostExpensiveRobot
    }

}