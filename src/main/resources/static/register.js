async function register() {
    const username = document.getElementById("username").value;
    const email = document.getElementById("email").value;

    const password = document.getElementById("password").value;

    const response = await fetch("/api/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username,email, password })
    });

    if (response.ok) {
        alert("Registration successful! Please login.");
        window.location.href = "login.html";
    } else {
        document.getElementById("error").innerText = "Registration failed!";
    }
}
