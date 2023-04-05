package com.rasyidin.moneyverse.data.local.db

import android.content.Context
import androidx.compose.ui.graphics.toArgb
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.data.local.db.converter.AnggaranConverter
import com.rasyidin.moneyverse.data.local.db.converter.CategoryConverter
import com.rasyidin.moneyverse.data.local.db.converter.TransactionConverter
import com.rasyidin.moneyverse.data.local.entities.account.AccountDao
import com.rasyidin.moneyverse.data.local.entities.account.AccountEntity
import com.rasyidin.moneyverse.data.local.entities.anggaran.AnggaranDao
import com.rasyidin.moneyverse.data.local.entities.anggaran.AnggaranEntity
import com.rasyidin.moneyverse.data.local.entities.category.CategoryDao
import com.rasyidin.moneyverse.data.local.entities.category.CategoryEntity
import com.rasyidin.moneyverse.data.local.entities.transaction.TransactionDao
import com.rasyidin.moneyverse.data.local.entities.transaction.TransactionEntity
import com.rasyidin.moneyverse.domain.model.category.CategoryKey
import com.rasyidin.moneyverse.domain.model.category.CategoryType
import com.rasyidin.moneyverse.ui.theme.*
import com.rasyidin.moneyverse.utils.DateUtils

@Database(
    entities = [
        AccountEntity::class,
        CategoryEntity::class,
        TransactionEntity::class,
        AnggaranEntity::class
    ],
    version = MoneyVerseDb.VERSION_DB,
    exportSchema = false
)
@TypeConverters(TransactionConverter::class, CategoryConverter::class, AnggaranConverter::class)
abstract class MoneyVerseDb : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
    abstract fun anggaranDao(): AnggaranDao

    companion object {
        const val VERSION_DB = 1
        const val DATABASE_NAME = "money_verse.db"
        const val PREF_NAME = "MoneyVersePref"

        fun initDefaultAccount(context: Context) : List<AccountEntity> {
            return listOf(
                AccountEntity(
                    id = 0,
                    name = context.getString(R.string.cash),
                    nominal = 0,
                    updatedAt = DateUtils.getCurrentDate(),
                    iconPath = R.drawable.ic_cash,
                    bgColor = ColorBgGreen.toArgb(),
                    desc = ""
                )
            )
        }

        fun initCategories(context: Context): List<CategoryEntity> {
            val categories = mutableListOf<CategoryEntity>()
            categories.apply {
                add(CategoryEntity(CategoryKey.FOOD, ColorBgRed.toArgb(), R.drawable.ic_food_n_drink, context.getString(R.string.makan_dan_minum), CategoryType.TransactionOutcome))
                add(CategoryEntity(CategoryKey.SHOP, ColorBgLightBlue.toArgb(), R.drawable.ic_belanja, context.getString(R.string.belanja), CategoryType.TransactionOutcome))
                add(CategoryEntity(CategoryKey.TRANSPORT, ColorBgBlue.toArgb(), R.drawable.ic_kendaraan, context.getString(R.string.kendaraan), CategoryType.TransactionOutcome))
                add(CategoryEntity(CategoryKey.BILL, ColorBgPurple.toArgb(), R.drawable.ic_tagihan, context.getString(R.string.tagihan), CategoryType.TransactionOutcome))
                add(CategoryEntity(CategoryKey.HEALTH, ColorBgGreen.toArgb(), R.drawable.ic_kesehatan, context.getString(R.string.kesehatan), CategoryType.TransactionOutcome))
                add(CategoryEntity(CategoryKey.HOLIDAY, ColorBgGreen.toArgb(), R.drawable.ic_liburan, context.getString(R.string.liburan), CategoryType.TransactionOutcome))
                add(CategoryEntity(CategoryKey.PET, ColorBgPink.toArgb(), R.drawable.ic_peliharaan, context.getString(R.string.peliharaan), CategoryType.TransactionOutcome))
                add(CategoryEntity(CategoryKey.INSURANCE, ColorBgPurple.toArgb(), R.drawable.ic_asuransi, context.getString(R.string.asuransi), CategoryType.TransactionOutcome))
                add(CategoryEntity(CategoryKey.ALMS, ColorBgRed.toArgb(), R.drawable.ic_sedekah, context.getString(R.string.sedekah), CategoryType.TransactionOutcome))
                add(CategoryEntity(CategoryKey.ENTERTAIN, ColorBgGreen.toArgb(), R.drawable.ic_hiburan, context.getString(R.string.hiburan), CategoryType.TransactionOutcome))
                add(CategoryEntity(CategoryKey.HOBBY, ColorBgYellow.toArgb(), R.drawable.ic_hobi, context.getString(R.string.hobi), CategoryType.TransactionOutcome))
                add(CategoryEntity(CategoryKey.SPORT, ColorBgPurple.toArgb(), R.drawable.ic_olahraga, context.getString(R.string.olahraga), CategoryType.TransactionOutcome))
                add(CategoryEntity(CategoryKey.SOCIAL, ColorBgYellow.toArgb(), R.drawable.ic_social, context.getString(R.string.sosial), CategoryType.TransactionOutcome))
                add(CategoryEntity(CategoryKey.ETC_TRANSACTION_OUTCOME, ColorBgBlue.toArgb(), R.drawable.ic_lain_lain, context.getString(R.string.lain_lain), CategoryType.TransactionOutcome))
                add(CategoryEntity(CategoryKey.CASH, ColorBgGreen.toArgb(), R.drawable.ic_cash, context.getString(R.string.cash), CategoryType.AddIncome))
                add(CategoryEntity(CategoryKey.ATM, ColorBgBlue.toArgb(), R.drawable.ic_atm, context.getString(R.string.atm), CategoryType.AddIncome))
                add(CategoryEntity(CategoryKey.WALLET_GREEN, ColorBgGreen.toArgb(), R.drawable.ic_wallet, context.getString(R.string.dompet), CategoryType.AddIncome))
                add(CategoryEntity(CategoryKey.WALLET_BLUE, ColorBgBlue.toArgb(), R.drawable.ic_wallet_blue, context.getString(R.string.dompet), CategoryType.AddIncome))
                add(CategoryEntity(CategoryKey.WALLET_RED, ColorBgRed.toArgb(), R.drawable.ic_wallet_red, context.getString(R.string.dompet), CategoryType.AddIncome))
                add(CategoryEntity(CategoryKey.WALLET_YELLOW, ColorBgYellow.toArgb(), R.drawable.ic_wallet_yellow, context.getString(R.string.dompet), CategoryType.AddIncome))
                add(CategoryEntity(CategoryKey.ATM_GREEN, ColorBgGreen.toArgb(), R.drawable.ic_atm_green, context.getString(R.string.atm), CategoryType.AddIncome))
                add(CategoryEntity(CategoryKey.ATM_RED, ColorBgRed.toArgb(), R.drawable.ic_atm_red, context.getString(R.string.atm), CategoryType.AddIncome))
                add(CategoryEntity(CategoryKey.ATM_YELLOW, ColorBgYellow.toArgb(), R.drawable.ic_atm_yellow, context.getString(R.string.atm), CategoryType.AddIncome))
                add(CategoryEntity(CategoryKey.ETC_ADD_INCOME, ColorBgBlue.toArgb(), R.drawable.ic_lain_lain, context.getString(R.string.atm), CategoryType.AddIncome))
                add(CategoryEntity(CategoryKey.SALARY, ColorBgGreen.toArgb(), R.drawable.ic_money_bag, context.getString(R.string.gaji), CategoryType.TransactionIncome))
                add(CategoryEntity(CategoryKey.BONUS, ColorBgBlue.toArgb(), R.drawable.ic_bonus, context.getString(R.string.bonus), CategoryType.TransactionIncome))
                add(CategoryEntity(CategoryKey.GIFT, ColorBgYellow.toArgb(), R.drawable.ic_gift, context.getString(R.string.pemberian), CategoryType.TransactionIncome))
                add(CategoryEntity(CategoryKey.BUSINESS, ColorBgGreen.toArgb(), R.drawable.ic_bisnis, context.getString(R.string.bisnis), CategoryType.TransactionIncome))
                add(CategoryEntity(CategoryKey.INVESTMENT, ColorBgLightBlue.toArgb(), R.drawable.ic_investasi_income, context.getString(R.string.investasi), CategoryType.TransactionIncome))
                add(CategoryEntity(CategoryKey.SALE, ColorBgPink.toArgb(), R.drawable.ic_sale, context.getString(R.string.penjualan), CategoryType.TransactionIncome))
                add(CategoryEntity(CategoryKey.ETC_TRANSACTION_INCOME, ColorBgBlue.toArgb(), R.drawable.ic_lain_lain, context.getString(R.string.lain_lain), CategoryType.TransactionIncome))
            }
            return categories
        }
    }
}