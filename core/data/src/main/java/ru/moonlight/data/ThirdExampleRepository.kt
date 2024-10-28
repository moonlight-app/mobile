package ru.moonlight.data

interface ThirdExampleRepository {
    fun getData(): String
}

class ThirdExampleRepositoryImpl : ThirdExampleRepository {
    override fun getData(): String = "Hello world from DI ThirdRepository"
}