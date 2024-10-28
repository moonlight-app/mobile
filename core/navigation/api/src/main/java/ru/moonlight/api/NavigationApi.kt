package ru.moonlight.api

interface NavigationApi<Direction> {
    fun navigate(direction: Direction)
}