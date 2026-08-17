<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <!DOCTYPE html>
    <html lang="en">

    <head>

        <meta charset="UTF-8">

        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>NDI Login</title>

        <!-- Inter Font -->
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">

        <!-- QR Code Library -->
        <script src="https://cdn.jsdelivr.net/npm/qrcode/build/qrcode.min.js"></script>

        <style>
            * {
                box-sizing: border-box;
            }

            /* ==============================
           PAGE
           ============================== */

            body {
                margin: 0;

                min-height: 100vh;

                display: flex;

                justify-content: center;

                align-items: center;

                font-family: 'Inter', sans-serif;

                background: linear-gradient(135deg,
                        #faf8f5 0%,
                        #f5f3f0 100%);

                color: #333;
            }


            /* ==============================
           MAIN NDI CARD
           ============================== */

            .ndi-container {

                width: 450px;

                max-width: 95%;

                background: #F8F8F8;

                border-radius: 28px;

                padding: 23px;

                text-align: center;

                box-shadow:
                    0 20px 50px rgba(0, 0, 0, 0.08);
            }


            /* ==============================
           TITLE
           ============================== */

            .title {

                font-size: 18px;

                font-weight: 700;

                margin: 0 0 30px 0;

                color: #222;

                line-height: 1.4;
            }


            .ndi-highlight {

                color: #5AC994;
            }


            /* ==============================
           QR AREA
           ============================== */

            .qr-wrapper {

                width: 200px;

                height: 200px;

                margin: 0 auto 30px auto;

                padding: 6px;

                border: 3px solid #57C08A;

                border-radius: 15px;

                background: #ffffff;

                display: flex;

                align-items: center;

                justify-content: center;
            }


            #qrcode {

                width: 190px;

                height: 180px;

                display: flex;

                align-items: center;

                justify-content: center;
            }


            #qrcode svg {

                width: 190px;

                height: 180px;

                display: block;
            }


            /* ==============================
           LOADER
           ============================== */

            .qr-loader {

                width: 190px;

                height: 180px;

                display: flex;

                flex-direction: column;

                align-items: center;

                justify-content: center;
            }


            .ndi-spinner {

                width: 55px;

                height: 55px;

                border: 5px solid #e5e5e5;

                border-top: 5px solid #236F67;

                border-radius: 50%;

                animation: spin 1s linear infinite;
            }


            .loader-text {

                margin-top: 12px;

                color: #777;

                font-size: 14px;
            }


            @keyframes spin {

                0% {
                    transform: rotate(0deg);
                }

                100% {
                    transform: rotate(360deg);
                }
            }


            /* ==============================
           INSTRUCTIONS
           ============================== */

            .instructions {

                font-size: 16px;

                color: #A1A0A0;

                text-align: left;

                max-width: 340px;

                margin: 0 auto 25px auto;

                padding-left: 20px;

                line-height: 1.5;
            }


            .instructions li {

                margin-bottom: 10px;
            }


            .scan-icon {

                height: 22px;

                width: auto;

                vertical-align: middle;

                margin: 0 3px;
            }


            /* ==============================
           VIDEO GUIDE
           ============================== */

            .video-guide-btn {

                width: 220px;

                height: 40px;

                border-radius: 30px;

                border: 2px solid #5AC994;

                background: transparent;

                color: #5AC994;

                font-size: 16px;

                font-weight: 500;

                cursor: pointer;

                transition: all 0.3s ease;

                margin: 0 auto 30px auto;

                display: flex;

                align-items: center;

                justify-content: center;

                gap: 10px;
            }


            .video-guide-btn:hover {

                background: #5AC994;

                color: white;

                transform: translateY(-2px);

                box-shadow:
                    0 8px 20px rgba(90, 201, 148, 0.3);
            }


            .play-icon {

                width: 18px;

                height: 18px;
            }


            /* ==============================
           DOWNLOAD SECTION
           ============================== */

            .download-text {

                font-size: 16px;

                color: #A1A0A0;

                margin-bottom: 10px;
            }


            .download-text span {

                color: #5AC994;

                font-weight: 800;

                cursor: pointer;
            }


            .store-badges {

                display: flex;

                justify-content: center;

                gap: 20px;

                margin-bottom: 30px;
            }


            .store-badges img {

                height: 48px;

                cursor: pointer;
            }


            /* ==============================
           SUPPORT
           ============================== */

            .support-section {

                text-align: center;
            }


            .support-title {

                font-size: 16px;

                color: #5AC994;

                font-weight: 700;

                margin-bottom: 10px;
            }


            .support-contacts {

                display: flex;

                justify-content: center;

                gap: 10px;

                margin-bottom: 10px;
            }


            .contact-item {

                display: flex;

                align-items: center;

                gap: 8px;
            }


            .contact-item img {

                width: 16px;

                height: 16px;
            }


            .contact-item a {

                color: #000;

                font-size: 14px;

                font-weight: 500;

                text-decoration: none;
            }


            /* ==============================
           MOBILE
           ============================== */

            @media (max-width: 600px) {

                body {

                    align-items: flex-start;

                    padding: 20px 0;
                }

                .ndi-container {

                    width: 95%;

                    padding: 25px 20px;

                    border-radius: 24px;
                }

                .title {

                    font-size: 18px;
                }

                .qr-wrapper {

                    width: 250px;

                    height: 250px;
                }

                .store-badges {

                    gap: 10px;
                }

                .store-badges img {

                    height: 42px;
                }

                .support-contacts {

                    flex-direction: column;

                    align-items: center;
                }
            }
        </style>

    </head>


    <body>


        <div class="ndi-container">


            <!-- ==========================================
         TITLE
         ========================================== -->

            <div class="title">

                Scan with

                <span class="ndi-highlight">
                    Bhutan NDI
                </span>

                Wallet

            </div>


            <!-- ==========================================
         QR CODE
         ========================================== -->

            <div class="qr-wrapper">

                <!-- Loader -->

                <div id="qr-loader" class="qr-loader">

                    <div class="ndi-spinner"></div>

                    <div class="loader-text">
                        Please wait...
                    </div>

                </div>


                <!-- QR -->

                <div id="qrcode" style="display:none;">
                </div>

            </div>


            <!-- ==========================================
         INSTRUCTIONS
         ========================================== -->

            <ol class="instructions">

                <li>
                    Open Bhutan NDI Wallet on your phone
                </li>

                <li>

                    Tap the Scan button

                    <img class="scan-icon" src="${pageContext.request.contextPath}/images/ScanButton.png" alt="Scan">

                    located on the menu bar and scan the QR code

                </li>

            </ol>


            <!-- ==========================================
         VIDEO GUIDE
         ========================================== -->

            <button type="button" class="video-guide-btn" onclick="openVideoGuide()">

                Watch Video Guide

                <img src="${pageContext.request.contextPath}/images/PlayButton.svg" class="play-icon" alt="Play">

            </button>


            <!-- ==========================================
         DOWNLOAD
         ========================================== -->

            <div class="download-text">

                Don't have the Bhutan NDI Wallet?

                <span onclick="downloadNDI()">
                    Download Now!
                </span>

            </div>


            <!-- ==========================================
         APP STORE BADGES
         ========================================== -->

            <div class="store-badges">

                <a href="https://play.google.com/store/apps/details?id=bt.gov.ndi.wallet" target="_blank">

                    <img src="https://upload.wikimedia.org/wikipedia/commons/7/78/Google_Play_Store_badge_EN.svg"
                        alt="Google Play">

                </a>


                <a href="#" onclick="return false;">

                    <img src="https://upload.wikimedia.org/wikipedia/commons/3/3c/Download_on_the_App_Store_Badge.svg"
                        alt="App Store">

                </a>

            </div>


            <!-- ==========================================
         SUPPORT
         ========================================== -->

            <div class="support-section">

                <div class="support-title">
                    Get Support
                </div>


                <div class="support-contacts">


                    <!-- Email -->

                    <div class="contact-item">

                        <img src="${pageContext.request.contextPath}/images/Mail.svg" alt="Email">

                        <a href="mailto:ndifeedback@bhutanndi.bt">
                            ndifeedback@bhutanndi.bt
                        </a>

                    </div>


                    <!-- Phone -->

                    <div class="contact-item">

                        <img src="${pageContext.request.contextPath}/images/Call.svg" alt="Phone">

                        <a href="tel:1199">
                            1199
                        </a>

                    </div>


                </div>

            </div>


        </div>
        <script>

            window.onload = function () {

                const qrUrl = "${qrUrl}";

                console.log("NDI QR URL:", qrUrl);


                if (!qrUrl || qrUrl.trim() === "") {

                    console.error(
                        "NDI QR URL is empty"
                    );

                    document.getElementById(
                        "qr-loader"
                    ).innerHTML = `
                <div style="
                    color:#d9534f;
                    font-size:14px;
                ">
                    Unable to generate QR code.
                </div>
            `;

                    return;
                }


                generateQRCode(qrUrl);
            };


            function generateQRCode(url) {

                const qrContainer =
                    document.getElementById("qrcode");

                const loader =
                    document.getElementById("qr-loader");


                /*
                 * Keep loader visible while QR
                 * is being generated.
                 */

                loader.style.display = "flex";

                qrContainer.style.display = "none";

                qrContainer.innerHTML = "";


                QRCode.toString(

                    url,

                    {

                        type: "svg",

                        errorCorrectionLevel: "H",

                        margin: 2,

                        width: 230

                    },

                    function (error, svgData) {


                        if (error) {

                            console.error(
                                "QR generation failed:",
                                error
                            );

                            loader.innerHTML = `
                        <div style="
                            color:#d9534f;
                            font-size:14px;
                        ">
                            QR generation failed.
                        </div>
                    `;

                            return;
                        }


                        /*
                         * Put QR into page.
                         */

                        qrContainer.innerHTML =
                            svgData;


                        /*
                         * Hide loader.
                         */

                        loader.style.display = "none";

                        qrContainer.style.display =
                            "flex";


                        console.log(
                            "NDI QR generated successfully"
                        );

                    }
                );
            }


            function openVideoGuide() {

                window.open(
                    "https://www.youtube.com/@bhutannationaldigitalidentity",
                    "_blank"
                );
            }


            function downloadNDI() {

                window.open(
                    "https://play.google.com/store/apps/details?id=bt.gov.ndi.wallet",
                    "_blank"
                );
            }

        </script>
        <script>

            const threadId = "${threadId}";

            console.log(
                "NDI Thread ID:",
                threadId
            );


            let checking = true;


            async function checkNdiVerification() {

                if (!threadId || !checking) {

                    return;
                }


                try {

                    const response =
                        await fetch(
                            "/ndi/status/" + threadId
                        );


                    if (!response.ok) {

                        console.error(
                            "NDI status request failed"
                        );

                        setTimeout(
                            checkNdiVerification,
                            2000
                        );

                        return;
                    }


                    const result =
                        await response.json();


                    console.log(
                        "NDI verification status:",
                        result
                    );


                    // =================================================
                    // SUCCESS
                    // =================================================

                    if (
                        result.completed === true
                        &&
                        result.verified === true
                    ) {

                        checking = false;


                        console.log(
                            "NDI LOGIN SUCCESS"
                        );


                        console.log(
                            "ID Number:",
                            result.idNumber
                        );


                        console.log(
                            "Full Name:",
                            result.fullName
                        );


                        // =============================================
                        // REDIRECT TO TEST SUCCESS PAGE
                        // =============================================

                        window.location.href =
                            "/ndi-success?threadId="
                            + encodeURIComponent(threadId);


                        return;
                    }


                    // =================================================
                    // REJECTED
                    // =================================================

                    if (
                        result.completed === true
                        &&
                        result.verified === false
                    ) {

                        checking = false;


                        alert(
                            "NDI verification was rejected."
                        );


                        return;
                    }


                    // =================================================
                    // STILL WAITING
                    // =================================================

                    setTimeout(
                        checkNdiVerification,
                        2000
                    );


                } catch (error) {

                    console.error(
                        "Error checking NDI status:",
                        error
                    );


                    setTimeout(
                        checkNdiVerification,
                        2000
                    );
                }
            }


            // =========================================================
            // START CHECKING
            // =========================================================

            if (threadId) {

                checkNdiVerification();
            }

        </script>


    </body>

    </html>