package de.rohitmisra.doctolib.impfterminfinder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication(scanBasePackages = ["de.rohitmisra.doctolib.impfterminfinder"])
class Application
fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
