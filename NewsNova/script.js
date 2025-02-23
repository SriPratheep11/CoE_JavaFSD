document.addEventListener('DOMContentLoaded', function () {
  // Get elements
  const loginLink = document.getElementById('login-link');
  const loginLi = document.getElementById('login-li');
  const userLi = document.getElementById('user-li');
  const usernameSpan = document.getElementById('username');
  const logoutButton = document.getElementById('logout-button');
  const postButton = document.getElementById('post-button');
  const loginPopup = document.getElementById('login-popup');
  const loginForm = document.getElementById('login-form');
  const loginUsername = document.getElementById('login-username');
  const loginPassword = document.getElementById('login-password');
  const loginError = document.getElementById('login-error');
  const postPopup = document.getElementById('post-popup');
  const postForm = document.getElementById('post-form');
  const aboutLink = document.getElementById('about-link');
  const contactLink = document.getElementById('contact-link');
  const aboutPopup = document.getElementById('about-popup');
  const contactPopup = document.getElementById('contact-popup');
  const closeButtons = document.querySelectorAll('.close');
  const overlay = document.createElement('div');
  overlay.classList.add('overlay');

  // Add overlay to the body
  document.body.appendChild(overlay);

  // Show Login Popup
  loginLink.addEventListener('click', function (e) {
    e.preventDefault();
    loginPopup.style.display = 'block';
    overlay.style.display = 'block';
  });

  // Close Popups
  closeButtons.forEach(button => {
    button.addEventListener('click', function () {
      loginPopup.style.display = 'none';
      postPopup.style.display = 'none';
      aboutPopup.style.display = 'none';
      contactPopup.style.display = 'none';
      overlay.style.display = 'none';
      loginError.style.display = 'none'; // Hide error message
    });
  });

  // Close Popups when clicking outside
  overlay.addEventListener('click', function () {
    loginPopup.style.display = 'none';
    postPopup.style.display = 'none';
    aboutPopup.style.display = 'none';
    contactPopup.style.display = 'none';
    overlay.style.display = 'none';
    loginError.style.display = 'none'; // Hide error message
  });

  // Login Form Submission
  loginForm.addEventListener('submit', function (e) {
    e.preventDefault(); // Prevent form submission

    // Hardcoded credentials (replace with server-side validation in a real app)
    const validUsername = "admin";
    const validPassword = "admin";

    const enteredUsername = loginUsername.value;
    const enteredPassword = loginPassword.value;

    // Validate credentials
    if (enteredUsername === validUsername && enteredPassword === validPassword) {
      // Successful login
      loginLi.style.display = 'none'; // Hide Login link
      userLi.style.display = 'flex'; // Show Username and Logout button
      usernameSpan.textContent = enteredUsername; // Set username
      postButton.style.display = 'block'; // Show Post News button
      loginPopup.style.display = 'none'; // Hide Login popup
      overlay.style.display = 'none'; // Hide overlay
      loginError.style.display = 'none'; // Hide error message
    } else {
      // Invalid credentials
      loginError.style.display = 'block'; // Show error message
    }
  });

  // Logout functionality
  logoutButton.addEventListener('click', function () {
    alert('Logged out successfully!');
    loginLi.style.display = 'block'; // Show Login link
    userLi.style.display = 'none'; // Hide Username and Logout button
    postButton.style.display = 'none'; // Hide Post News button
  });

  // Post Button and Popup
  postButton.addEventListener('click', function () {
    postPopup.style.display = 'block';
    overlay.style.display = 'block';
  });

  // Post Form Submission
  postForm.addEventListener('submit', function (e) {
    e.preventDefault(); // Prevent form submission

    const category = document.getElementById('post-category').value;
    const title = document.getElementById('post-title').value;
    const content = document.getElementById('post-content').value;
    const media = document.getElementById('post-media').files[0];

    // Validate all fields
    if (category && title && content && media) {
      // Convert the image file to a Base64 string
      const reader = new FileReader();
      reader.onload = function (event) {
        const base64Image = event.target.result;

        // Create a news object with a unique ID
        const newsItem = {
          id: Date.now(), // Unique ID for each news item
          title: title,
          content: content,
          media: base64Image, // Store the Base64 string
        };

        // Save news to localStorage
        const categoryNews = JSON.parse(localStorage.getItem(category)) || [];
        categoryNews.push(newsItem);
        localStorage.setItem(category, JSON.stringify(categoryNews));

        alert('News submitted successfully!');
        postPopup.style.display = 'none'; // Close popup after submission
        overlay.style.display = 'none'; // Hide overlay

        // Clear form fields
        postForm.reset();

        // Redirect to the corresponding category page
        window.location.href = `${category}.html`;
      };

      // Read the image file as a Data URL (Base64)
      reader.readAsDataURL(media);
    } else {
      alert('Please fill all fields and upload an image/video.');
    }
  });

  // Show About Popup
  aboutLink.addEventListener('click', function (e) {
    e.preventDefault();
    aboutPopup.style.display = 'block';
    overlay.style.display = 'block';
  });

  // Show Contact Popup
  contactLink.addEventListener('click', function (e) {
    e.preventDefault();
    contactPopup.style.display = 'block';
    overlay.style.display = 'block';
  });
});