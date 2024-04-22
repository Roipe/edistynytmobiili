package com.example.edistynytmobiili

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase


//Määritellään account-taululle entity-luokka
@Entity(tableName = "account")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val accessToken: String
)

//Määritellään funktioita, joilla voidaan käsitellä tietokannan sisältöä
@Dao
abstract class AccountDao {

    //Tällä funktiolla voidaan lisätä token tauluun
    @Insert
    abstract suspend fun addToken(entity: AccountEntity)


    //Tällä funktiolla voidaan hakea token taulusta
    @Query("SELECT accessToken FROM account ORDER BY id DESC LIMIT 1;")
    abstract suspend fun getToken() : String?

    @Query("DELETE FROM account")
    abstract suspend fun removeTokens()

}

//Määritellään tietokannalle luokka ja funktio.
@Database(entities = [AccountEntity::class], version = 1)
abstract class AccountDatabase : RoomDatabase() {
    abstract fun accountDao() : AccountDao
}