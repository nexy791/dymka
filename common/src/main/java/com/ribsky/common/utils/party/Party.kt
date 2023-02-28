package com.ribsky.common.utils.party

import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Spread
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

object Party {

    val rain by lazy {
        nl.dionsegijn.konfetti.core.Party(
            speed = 0f,
            maxSpeed = 5f,
            damping = 0.9f,
            angle = Angle.BOTTOM,
            spread = Spread.ROUND,
            emitter = Emitter(duration = Long.MAX_VALUE, TimeUnit.SECONDS).perSecond(25),
            position = Position.Relative(0.0, 0.0).between(Position.Relative(1.0, 0.0))
        )
    }
}
