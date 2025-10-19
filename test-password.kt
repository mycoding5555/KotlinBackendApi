import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

fun main() {
    val encoder = BCryptPasswordEncoder()
    val rawPassword = "123456"
    val storedHash = "\$2a\$10\$SUtjwLk3ZJYFdKJEx/wL0eFrK8VRMXmK91Yqt2w1b87Wu174pq4v."
    
    println("Testing password verification:")
    println("Raw password: $rawPassword")
    println("Stored hash: $storedHash")
    println("Match result: ${encoder.matches(rawPassword, storedHash)}")
    
    // Also test generating a new hash
    val newHash = encoder.encode(rawPassword)
    println("New hash for same password: $newHash")
    println("New hash matches: ${encoder.matches(rawPassword, newHash)}")
}