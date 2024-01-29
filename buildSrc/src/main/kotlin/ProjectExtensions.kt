import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project

fun Project.android(): LibraryExtension {
    return extensions.getByType(LibraryExtension::class.java)
}