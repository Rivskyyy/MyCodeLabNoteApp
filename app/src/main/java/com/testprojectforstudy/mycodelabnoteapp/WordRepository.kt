package com.testprojectforstudy.mycodelabnoteapp

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao : WordDao) {

    val allWords : Flow<List<Word>> = wordDao.getAlphabetizedWords()

    @Suppress("RedunantSuspendModifier")
    @WorkerThread
    suspend fun insert (word : Word ){
        wordDao.insert(word)
    }
}