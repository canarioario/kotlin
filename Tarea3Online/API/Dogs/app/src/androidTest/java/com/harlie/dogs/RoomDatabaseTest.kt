package com.harlie.dogs

import android.content.Context
import androidx.room.Room
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.harlie.dogs.model.DogBreed
import com.harlie.dogs.room.DogDatabase
import com.harlie.dogs.util.GlideWrapper
import com.harlie.dogs.view.MainActivity
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsEqual.equalTo
import org.junit.*
import org.junit.Assert.assertThat
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class RoomDatabaseTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    private lateinit var db: DogDatabase

    companion object {
        @BeforeClass
        @JvmStatic
        fun preInitialization() {
            println("preInitialization")
            GlideWrapper.isUnitTest = true
        }

        @AfterClass
        @JvmStatic
        fun allTestsComplete() {
            println("allTestsComplete")
        }
    }

    @Before
    fun createDb() {
        println("createDb")
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, DogDatabase::class.java)
            .allowMainThreadQueries().build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        println("closeDb")
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeOneDogThenReadOneDog() {
        println("writeOneDogThenReadOneDog")
        val dogList: List<DogBreed> = TestUtil().createTestDogs(3)
        dogList.forEach {dog ->
            println("dog: $dog")
            runBlocking {
                db.dogDao().insertAll(dog) // inserts one dog
                val breedId = dog.breedId
                assert(breedId != null)
                val byId = db.dogDao().getDog(breedId!!)
                assertThat(byId.breedId, equalTo(dog.breedId))
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertDogsListThenReplaceAll() {
        println("insertDogsListThenReplaceAll")
        val dogList: List<DogBreed> = TestUtil().createTestDogs(3)
        val dogReplaceList: List<DogBreed> = TestUtil().createTestDogs(5)
        val dao = db.dogDao()
        runBlocking {
            val nodogs = dao.getAllDogs()
            assertThat(nodogs.size, equalTo(0))
            dao.insertAll(*dogList.toTypedArray())
            val threedogs = dao.getAllDogs()
            assertThat(threedogs.size, equalTo(3))
            dao.deleteAllDogs()
            val dogsgone = dao.getAllDogs()
            assertThat(dogsgone.size, equalTo(0))
            dao.insertAll(*dogReplaceList.toTypedArray())
            val fivedogs = dao.getAllDogs()
            assertThat(fivedogs.size, equalTo(5))
            dao.deleteAllDogs()
            val dogsagaingone = dao.getAllDogs()
            assertThat(dogsagaingone.size, equalTo(0))
            dao.insertAll(*dogList.toTypedArray())
            val threedogsagain = dao.getAllDogs()
            assertThat(threedogsagain.size, equalTo(3))
        }
    }

}
