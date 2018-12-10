package viewmodel

import android.content.Context
import android.content.SharedPreferences

class SharedPrefsStorage(context: Context) {
//    private val mPreference: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
//    private val mEditor: SharedPreferences.Editor
//
//    /**
//     * get device unique uid from prefs
//     *
//     * @return String UID if it was saved earlier, else null
//     */
//    var deviceUid: String?
//        get() = mPreference.getString(DEVICE_UID, null)
//        set() = ""
//
//    /**
//     * Get server token from SP
//     *
//     * @return String token
//     */
//    val serverToken: String?
//        get() = mPreference.getString(SERVER_TOKEN, null)
//
//    /**
//     * Get user logged in flag
//     *
//     * @return true if logged with SN, false (or not set) for anonymous
//     */
//    val userLoggedFlag: Boolean
//        get() = mPreference.getBoolean(LOGGED_WITH_SOCIAL, false)
//
//    val dailyWords: DayResponse
//        get() {
//            val storedResponse = mPreference.getString(DAILY_WORDS, "")
//            return if (storedResponse!!.isEmpty()) DayResponse() else Gson().fromJson(storedResponse, DayResponse::class.java)
//        }
//
//    /**
//     * Get user statistics from local storage
//     *
//     * @return [UserResponse] or null if storage is empty
//     */
//    val userStat: UserResponse?
//        get() {
//            val json = mPreference.getString(USER_STAT, "")
//            return if (!json!!.isEmpty()) {
//                Gson().fromJson(json, UserResponse::class.java)
//            } else {
//                null
//            }
//        }
//
//    /**
//     * Weather or not show Intro
//     *
//     * @return false if intro was not shown yet
//     */
//    val showIntro: Boolean
//        get() = mPreference.getBoolean(INTRO_SHOW, false)
//
//    /**
//     * Get new words notification time
//     *
//     * @return time in milliseconds
//     */
//    val notificationTime: String?
//        get() = mPreference.getString(NOTIF_TIME, null)
//
//    /**
//     * Get users rating from local storage
//     *
//     * @return [UsersRatingResponse] or null if storage is empty
//     */
//    val usersRating: UsersRatingResponse?
//        get() {
//            val json = mPreference.getString(USERS_RATING, "")
//            return if (!json!!.isEmpty()) {
//                Gson().fromJson(json, UsersRatingResponse::class.java)
//            } else {
//                null
//            }
//        }
//
//    init {
//        this.mEditor = mPreference.edit()
//    }
//
//    /**
//     * Store device uid in SharedPreferences
//     *
//     * @param uid
//     */
//    fun saveDeviceUid(uid: String) {
//        mEditor.putString(DEVICE_UID, uid).commit()
//    }
//
//    /**
//     * Save user token in local storage
//     *
//     * @param token string user token
//     */
//    fun saveServerToken(token: String) {
//        mEditor.putString(SERVER_TOKEN, token).commit()
//    }
//
//    /**
//     * Set flag if user logged in with social network
//     *
//     * @param flag true if logged with SN, false (or not set) for anonymous
//     */
//    fun setUserLoggedIn(flag: Boolean) {
//        mEditor.putBoolean(LOGGED_WITH_SOCIAL, flag).commit()
//    }
//
//    fun saveDailyWords(words: DayResponse) {
//        mEditor.putString(DAILY_WORDS, Gson().toJson(words)).commit()
//    }
//
//    /**
//     * Save user stat in local storage
//     *
//     * @param stat [UserResponse]
//     */
//    fun saveUserStat(stat: UserResponse) {
//        this.mEditor.putString(USER_STAT, Gson().toJson(stat)).commit()
//    }
//
//    fun saveIntroShownStatus(shown: Boolean) {
//        this.mEditor.putBoolean(INTRO_SHOW, shown).commit()
//    }
//
//    fun saveNotificationTime(time: String?) {
//        this.mEditor.putString(NOTIF_TIME, time).commit()
//    }
//
//    fun saveUsersRating(rating: UsersRatingResponse) {
//        this.mEditor.putString(USERS_RATING, Gson().toJson(rating)).commit()
//    }
//
//    companion object {
//
//        private const val USER_ADDRESS = "user_address"
//        private const val ADMIN_ADDRESS = "admin_address"
//        private const val APP_ADDRESS = "app_address"
//        private const val TOKEN_ADDRESS = "token_address"
//        private const val GAME_ADDRESS = "game_address"
//        private const val CHARACTER_ADDRESS = "character_address"
//        private const val SERVER = "server"
//        private const val CHARACTER = "character"
//    }
}
