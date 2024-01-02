package cc.loac.nebula

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NebulaApplication

fun main(args: Array<String>) {
	runApplication<NebulaApplication>(*args)
}
