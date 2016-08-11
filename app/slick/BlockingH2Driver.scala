package slick

import slick.driver.H2Driver
import com.github.takezoe.slick.blocking.SlickBlockingAPI

object BlockingH2Driver extends H2Driver with SlickBlockingAPI
