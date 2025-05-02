function logout() {
    localStorage.removeItem("jwt");
    window.location.href = "login.html";
}

// ✅ Protect this page
const token = localStorage.getItem("jwt");
if (!token) {
    alert("Please login first!");
    window.location.href = "login.html";
}
