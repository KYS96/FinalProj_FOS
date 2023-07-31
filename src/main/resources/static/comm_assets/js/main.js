(function () {
  "use strict";

  // ======= Sticky
  window.onscroll = function () {
    const ud_header = document.querySelector(".ud-header");
    const sticky = ud_header.offsetTop;
    // const logo = document.querySelector(".navbar-brand img");
    const word = document.querySelector(".navbar-brand p");


    // if (window.pageYOffset > sticky) {
    if (window.scrollY > sticky) {
      ud_header.classList.add("sticky");
    } else {
      ud_header.classList.remove("sticky");
    }

    // === logo change
    if (ud_header.classList.contains("sticky")) {
      // logo.src = "image/Forest -2.png";
      word.style.color = "gray";
    } else {
      // logo.style.display = "none";
      word.style.color = "white";
    }
  };



  //===== close navbar-collapse when a  clicked
  let navbarToggler = document.querySelector(".navbar-toggler");
  const navbarCollapse = document.querySelector(".navbar-collapse");

  document.querySelectorAll(".ud-menu-scroll").forEach((e) =>
    e.addEventListener("click", () => {
      navbarToggler.classList.remove("active");
      navbarCollapse.classList.remove("show");
    })
  );

  navbarToggler.addEventListener("click", function () {
    navbarToggler.classList.toggle("active");
    navbarCollapse.classList.toggle("show");
  });

  // ===== submenu
  const submenuButton = document.querySelectorAll(".nav-item-has-children");
  submenuButton.forEach((elem) => {
    elem.querySelector("a").addEventListener("click", () => {
      elem.querySelector(".ud-submenu").classList.toggle("show");
    });
  });

  // ===== wow js wow는 시간차로 나타나는 그런것을 뜻합니다 ㅎㅎ;
  // new WOW().init();

  // // ====== scroll top js
  // function scrollTo(element, to = 0, duration = 500) {
  //   const start = element.scrollTop;
  //   const change = to - start;
  //   const increment = 20;
  //   let currentTime = 0;

  //   const animateScroll = () => {
  //     currentTime += increment;

  //     const val = Math.easeInOutQuad(currentTime, start, change, duration);

  //     element.scrollTop = val;

  //     if (currentTime < duration) {
  //       setTimeout(animateScroll, increment);
  //     }
  //   };

  //   animateScroll();
  // }

  // Math.easeInOutQuad = function (t, b, c, d) {
  //   t /= d / 2;
  //   if (t < 1) return (c / 2) * t * t + b;
  //   t--;
  //   return (-c / 2) * (t * (t - 2) - 1) + b;
  // };

  // document.querySelector(".back-to-top").onclick = () => {
  //   scrollTo(document.documentElement);
  // };
})();
