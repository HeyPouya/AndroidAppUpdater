package ir.heydarii.appupdater.exception

class PermissionNotGrantedException : Exception() {

    override val message: String?
        get() = "You haven't provided WRITE_EXTERNAL_STORAGE permission"
}