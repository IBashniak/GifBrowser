package com.example.serializable.ui




internal class QueryValidator private constructor() {
    companion object {
        private const val MIN_QUERY_LENGTH = 3
        fun isValid(search: String): Boolean {
            return search.length >= MIN_QUERY_LENGTH
        }
}
    init {
        throw RuntimeException("There is must be no instance!")
    }
}
