package com.testprojectforstudy.mycodelabnoteapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false )

abstract class WordRoomDatabase : RoomDatabase(){

    abstract fun wordDao() : WordDao

    private class WordDatabaseCallback(private val scope : CoroutineScope) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {database ->
                scope.launch {
                    var wordDao = database.wordDao()

                    //Delete all content here
                    wordDao.deleteAll()

                    //Add sample words
                    var word = Word(word = "Hello", id = 1)
                    wordDao.insert(word)
                    word = Word(2, "World ")
                    wordDao.insert(word)

                    //TODO : Add your own worlds!
                    word = Word(3 , "TODO!")
                    wordDao.insert(word)


                }
            }
        }
    }
    companion object{

        @Volatile
        private var INSTANCE : WordRoomDatabase? = null

        fun getDatabase(context : Context, scope : CoroutineScope) : WordRoomDatabase {

            return INSTANCE ?:  synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database").build()
                INSTANCE = instance
                instance
            }


        }
    }

}
