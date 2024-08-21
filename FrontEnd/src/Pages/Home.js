import React from 'react'
import '../styles/home.css'


export default function Home() {
  return (
    <div className='home'>
    <head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Simple Homepage</title>
    <link rel="stylesheet" href="styles.css"/>
   </head>
   <body>
    <header>
        <div class="container">
            <h1>LanceStack</h1>
            <nav>
                <ul>
                    <li><a href="Home">Home</a></li>
                    <li><a href="Login">Login</a></li>
                    <li><a href="Signup">SignUp</a></li>
                    <li><a href="#contact">About</a></li>
                </ul>
            </nav>
        </div>
    </header>
    <section class="hero">
        <div class="container">
            <h4>Your Freelance Partner for Cutting-Edge Tech Solutions.</h4>
            <a href="#about" class="btn">Learn More</a>
        </div>
    </section>

    <section class="content">
        <div class="container">
            <h2>About Us</h2>
            <p>At Lancestack, we connect visionary tech projects with top-tier freelance talent. Founded on the principles of innovation and collaboration, our platform is designed to bring together skilled professionals and dynamic companies to create cutting-edge solutions.Our team is passionate about transforming ideas into reality,With a diverse pool of experts specializing in various tech domains, Lancestack is your go-to resource for finding the right fit for your project needs.</p>
<h2>Services</h2>
<p>At Lancestack, we specialize in connecting you with top-tier freelance talent for your tech projects. Our services include expert freelance matching to ensure you find the right skills for your needs, and custom tech solutions tailored to your project’s requirements. along with access to specialized skillsets in areas like web development,  Our flexible engagement models and commitment to quality guarantee that you receive exceptional results and scalable solutions.</p>
           </div>
    </section>

    <footer>
        <div class="container">
            <p>&copy; 2024 My Website. All rights reserved.</p>
        </div>
    </footer>
</body>
</div>
  )
}