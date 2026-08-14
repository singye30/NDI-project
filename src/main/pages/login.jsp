<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background: linear-gradient(135deg, #faf8f5 0%, #f5f3f0 100%);
            margin: 0;
            justify-content: center;
        }
        .popup {
            width: 590px;
            background: white;
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(93, 64, 55, 0.1);
            padding: 40px 30px;
            text-align: center;
        }
        .title {
            font-size: 20px;
            font-weight: 600;
            color: #2e2e2e;
            margin-bottom: 32px;
            line-height: 1.4;
        }
        .btn {
            width: 320px;
            height: 52px;
            border: none;
            border-radius: 10px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            margin: 14px auto;
            display: block;
            box-shadow: 0 4px 10px rgba(0,0,0,0.08);
        }
        .btn-ndi {
            background: #1A4D38; /* Deep forest green – NDI brand */
            color: white;
        }
        .btn-email {
            background: #5D4037; /* Rich timber brown – rural/forest feel */
            color: white;
        }
        .btn:hover {
            transform: translateY(-3px);
            box-shadow: 0 6px 14px rgba(0,0,0,0.15);
        }
        .btn:active {
            transform: translateY(0);
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }

        .divider {
            width: 100%;
            height: 1px;
            background: linear-gradient(90deg, transparent, #e0dbd5, transparent);
            margin: 28px auto;
            position: relative;
        }
        .divider::after {
            content: "or";
            position: absolute;
            top: -10px;
            left: 50%;
            transform: translateX(-50%);
            background: #faf8f5;
            padding: 0 12px;
            font-size: 14px;
            color: #7a7572;
            font-weight: 500;
        }
        .ndi-official-btn {
            width: 330px;
            height: 50px;
            background-color: #124143;
            color: #FFFFFF;
            border: 1px solid #124143;
            border-radius: 10px; /* Rectangle shape to match your UI */
            font-family: 'Inter', sans-serif;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 14px auto;
            transition: all 0.25s ease;
        }

        .ndi-btn-content {
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .ndi-btn-logo {
            height: 22px;
            width: auto;
            margin-right: 10px; /* Required minimum spacing */
        }

        /* Hover effect (elevation + shadow) */
        .ndi-official-btn:hover {
            transform: translateY(-3px);
            box-shadow: 0 6px 14px rgba(0, 0, 0, 0.15);
        }
        /* NDI Model*/
        #ndiApplyModal,
        #ndiApplyModal * {
            font-family: 'Inter', sans-serif;

        }
        #ndiApplyModal {
            display: flex !important;
            align-items: center;
            justify-content: center;
            margin: 30px;

        }
        #ndiApplyModal .modal-dialog {
            max-width: 590px;
            max-height: 750px;
        }

        #ndiApplyModal .modal-content {
            background:  #F8F8F8;
            border-radius: 28px;
            padding: 30px 30px;
            border: none;
            box-shadow: 0 20px 50px rgba(0,0,0,0.08);
            text-align: center;
        }

        #ndiApplyModal .modal-header {
            border-bottom: none;
            justify-content: center;
            padding: 0;
            margin-bottom: 30px; /* 30px spacing */
        }

        #ndiApplyModal .modal-title {
            font-size: 18px;
            font-weight: 700;
            margin: 0;
        }

        .ndi-highlight {
            color: #5AC994;
        }

        #ndiApplyModal .btn-close {
            position: absolute;
            top: 25px;
            right: 25px;
            background: none;
            border: none;
            padding: 0;
            cursor: pointer;
        }
        #ndiApplyModal .btn-close img{
            width:40px;
            height:40px;

        }

        #ndiApplyModal .modal-body {
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;  /* CENTER EVERYTHING */
        }

        .ndi-section:last-child {
            margin-bottom: 0;
        }
        .qr-wrapper {
            display: inline-block;
            padding: 8px;              /* space between QR and border */
            border: 3px solid #57C08A;
            border-radius: 15px;
            background: #ffffff;
            margin-bottom: 30px;
        }

        /* QR size */
        #qrcode-main canvas,
        #qrcode-main img {
            width: 230px !important;
            height: 230px !important;
            border: none;
            padding: 0;
            background: transparent;
        }

        .qr-instructions {
            font-size: 16px;
            color: #A1A0A0;
            text-align: left;
            max-width: 340px;
            padding-left: 20px;
        }

        .qr-instructions li {
            margin-bottom: 10px;
        }

        .video-guide-btn {
            width: 220px;
            height: 40px;
            border-radius: 30px;
            border: 2px solid #5AC994;
            background: transparent;
            color: #5AC994;
            font-size: 16px;
            font-weight: 500;
            transition: all 0.3s ease;
            margin-bottom: 30px;
            display:flex;
            align-items:center;
            justify-content:center;
            gap:10px;
        }
        .play-icon{
            width:18px;
            height:18px;
        }
        .download-text {
            font-size: 16px;
            color: #A1A0A0;
            margin-bottom: 10px;
        }

        .download-text span {
            color: #5AC994;
            font-weight: 800;
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

        .support-section {
            text-align: center;
        }

        .support-title {
            font-size: 16px;
            color: #5AC994;
            font-weight: 700;
            margin-bottom:10px !important;
        }
        .support-contacts{
            display:flex;
            justify-content:center;
            gap:10px;
            margin-bottom:20px;
        }

        .contact-item{
            display:flex;
            align-items:center;
            gap:8px;
        }

        .contact-item img{
            width:16px;
            height:16px;
        }

        .contact-item a{
            color:#000;
            font-size:14px;
            font-weight: 500;
            text-decoration:none;
        }
        .qr-loader{
            width:230px;
            height:230px;
            display:flex;
            flex-direction:column;
            align-items:center;
            justify-content:center;
        }

        .loader-text{
            margin-top:10px;
            color:#777;
            font-size:14px;
        }

        /* Spinner */
        .ndi-spinner{
            width:40px;
            height:40px;
            border:4px solid #e5e5e5;
            border-top:4px solid #236F67;
            border-radius:50%;
            animation: spin 1s linear infinite;
        }

        @keyframes spin{
            0%{ transform:rotate(0deg); }
            100%{ transform:rotate(360deg); }
        }
        .scan-icon {
            height: 22px;
            vertical-align: middle;
            margin-top: 2px;
        }
        #ndiMobileBtn{
            display:none;
        }
        .btn-open {
            background-color: #5AC994;
            color: white;
            border: none;
            padding: 12px;
            border-radius: 6px;
            max-width: 300px;
            width: 100%;
            font-size: 16px;
            font-weight: 500;
            cursor: pointer;
            transition: background-color 0.3s ease;
            margin: 0 auto 30px auto;   /* centers button */
            display: block;
        }

        .btn-open:hover {
            background-color: #45a97b;   /* darker green on hover */
        }
        .video-guide-btn:hover {
            background-color: #5AC994;
            color: #ffffff;
            border-color: #5AC994;
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(90, 201, 148, 0.3);
        }

        /* Optional: icon color change on hover */
        .video-guide-btn:hover .play-icon {
            filter: brightness(0) invert(1);
        }

        /* Main layout */
        .main-container {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .popup { margin-top: 30px; }
    </style>
</head>
<body>
<c:choose>
    <c:when test="${true}">
        <div class="modal fade" id="ndiApplyModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="ndiModalTitle">
                            Scan with <span class="ndi-highlight">Bhutan NDI</span> Wallet
                        </h5>
                    </div>
                    <button type="button" class="btn-open" id="ndiMobileBtn" onclick="openNDIWallet()">
                        Open Bhutan NDI Wallet
                    </button>
                    <div class="modal-body">

                        <!-- QR CODE -->
                        <div class="qr-wrapper ndi-section">

                            <!-- LOADER -->
                            <div id="ndiQrLoader" class="qr-loader">
                                <div class="ndi-spinner"></div>
                                <div class="loader-text">Please wait...</div>
                            </div>

                            <div id="qrcode-main" style="display:none;"></div>

                        </div>
                        <ol class="qr-instructions ndi-section">
                            <li>Open Bhutan NDI Wallet on your phone</li>
                            <li>
                                Tap the Scan button
                                <img class="scan-icon"
                                     src="${pageContext.request.contextPath}/images/ScanButton.png" />
                                located on the menu bar and scan the QR code
                            </li>
                        </ol>

                        <!-- VIDEO GUIDE BUTTON -->
                        <button class="video-guide-btn ndi-section" onclick="window.open('https://www.youtube.com/@bhutannationaldigitalidentity','_blank')">
                            Watch Video Guide
                            <img src="${pageContext.request.contextPath}/images/PlayButton.svg"
                                 class="play-icon ms-2">
                        </button>

                        <!-- DOWNLOAD TEXT -->
                        <div class="download-text ndi-section">
                            Don’t have the Bhutan NDI Wallet?
                            <span style="cursor:pointer; color:#5AC994;"
                                  onclick="window.open('https://play.google.com/store/apps/details?id=bt.gov.ndi.wallet','_blank')">
                                Download Now!
                            </span>
                        </div>

                        <div class="store-badges ndi-section">
                            <a href="https://play.google.com/store/apps/details?id=bt.gov.ndi.wallet" target="_blank">
                                <img src="https://upload.wikimedia.org/wikipedia/commons/7/78/Google_Play_Store_badge_EN.svg"
                                     alt="Google Play">
                            </a>
                            <a href="#" target="_blank">
                                <img src="https://upload.wikimedia.org/wikipedia/commons/3/3c/Download_on_the_App_Store_Badge.svg"
                                     alt="App Store">
                            </a>
                        </div>

                        <!-- SUPPORT SECTION -->
                        <div class="support-section">
                            <div class="support-title">Get Support</div>

                            <div class="support-contacts">

                                <div class="contact-item">
                                    <img src="${pageContext.request.contextPath}/images/Mail.svg">
                                    <a href="mailto:ndifeedback@bhutanndi.bt">ndifeedback@bhutanndi.bt</a>
                                </div>

                                <div class="contact-item">
                                    <img src="${pageContext.request.contextPath}/images/Call.svg">
                                    <a href="tel:1199">1199</a>
                                </div>

                            </div>
                        </div>

                    </div>

                </div>
            </div> <!-- modal-dialog -->

        </div> <!-- ndiApplyModal -->
        <script src="https://cdn.jsdelivr.net/npm/qrcode/build/qrcode.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js" integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <!-- AUTO SESSION POLLING -->
        <script type="text/javascript">

            let evtSource = null;
            let ndiDeepLinkURL = null;

            startListening(`${threadId}`);

            function startListening(threadId) {
                if (evtSource) evtSource.close();

                const url = `${pageContext.request.contextPath}/rc/events/`+threadId;
                evtSource = new EventSource(url);

                evtSource.addEventListener("status", (e) => {
                    const data = JSON.parse(e.data);
                    // console.log("PUSH status:", data.data);

                    if (data.status === "PROOF_VALIDATED") {
                        // optionally redirect or render attributes:
                        // console.log(data.revealedAttrs); hit issue credential endpoint
                        handleLogin(data)

                    // } else if (data.status === "PROOF_REJECTED" || data.status === "PROOF_FAILED") {
                    //     alert("Proof ended: " + data.status);
                    // }
                    } else if (data.status === "PROOF_REJECTED") {
                        alert("Proof Validation Declined");

                    } else if (data.status === "PROOF_FAILED") {
                        alert("NDI verification failed. Please try again.");
                    }

                    evtSource.close(); // one-shot
                });

                evtSource.onerror = (err) => {
                    console.error("SSE error:", err);
                    // optionally show a “Reconnect” / fallback button
                };
            }

            function handleLogin(data){

                console.log("FULL RESPONSE:", data);

                const revealed = data?.data?.revealedAttrs;

                if (!revealed) {
                    alert("No attributes received from NDI");
                    console.error("Missing revealedAttrs:", data);
                    return;
                }

                const idNumber = revealed["ID Number"]?.[0]?.value;
                const fullName = revealed["Full Name"]?.[0]?.value;

                console.log("Extracted:", { idNumber, fullName});

                if (!idNumber) {
                    alert("ID Number missing from NDI response");
                    return;
                }

                $.ajax({
                    url: `${pageContext.request.contextPath}/ndiLandingPage.html`,
                    method: "POST",
                    contentType: "application/json",
                    data: JSON.stringify({
                        cid: idNumber,
                        fullName: fullName,
                        gewog: gewog,
                        dzongkhag: dzongkhag
                    }),
                    success: (res)=>{
                        console.log("LOGIN RESPONSE:", res);

                        if(res === "Success"){
                            location.href = `${pageContext.request.contextPath}/redirect.html`;
                        } else {
                            alert("Error while logging in. Please try again.");
                        }
                    },
                    error: (err)=>{
                        console.error("AJAX ERROR:", err);
                        alert("Server error during login");
                    }
                });
            }
            window.onload = function () {

                const qrUrl = "${qrUrl}";
                const threadId = "${threadId}";
                const deepLink = "${deepLink}";

                if(qrUrl){
                    generateNDIQRCode(qrUrl);
                }

                if (deepLink && deepLink !== "null") {
                    ndiDeepLinkURL = deepLink;
                }

                if(threadId){
                    startListening(threadId);
                }

            };
            function generateNDIQRCode(url) {

                const container = document.getElementById("qrcode-main");
                const loader = document.getElementById("ndiQrLoader");

                // Show loader
                loader.style.display = "flex";
                container.style.display = "none";

                container.innerHTML = "";

                // Small delay so loader becomes visible
                setTimeout(() => {

                    QRCode.toString(url, {
                        type: "svg",
                        errorCorrectionLevel: "H",
                        margin: 2,
                        width: 200
                    }, function (err, svgData) {

                        if (err) {
                            console.error(err);
                            return;
                        }

                        container.innerHTML = svgData;

                        const svg = container.querySelector("svg");

                        const size = svg.viewBox.baseVal.width;
                        const logoSize = size * 0.22;

                        const bgCircle = document.createElementNS(
                            "http://www.w3.org/2000/svg",
                            "circle"
                        );

                        bgCircle.setAttribute("cx", size / 2);
                        bgCircle.setAttribute("cy", size / 2);
                        bgCircle.setAttribute("r", logoSize / 2 + 2);
                        bgCircle.setAttribute("fill", "#ffffff");

                        svg.appendChild(bgCircle);

                        const logo = document.createElementNS(
                            "http://www.w3.org/2000/svg",
                            "image"
                        );

                        logo.setAttributeNS(
                            "http://www.w3.org/1999/xlink",
                            "href",
                            window.location.origin + "/resources/images/NDIlogobg.png"
                        );

                        logo.setAttribute("x", (size - logoSize) / 2);
                        logo.setAttribute("y", (size - logoSize) / 2);
                        logo.setAttribute("width", logoSize);
                        logo.setAttribute("height", logoSize);

                        svg.appendChild(logo);

                        // Hide loader after QR loads
                        loader.style.display = "none";
                        container.style.display = "block";

                    });

                }, 500); // 0.5s delay so loader is visible
            }
            function isMobile() {
                return /Android|iPhone|iPad|iPod|Mobile/i.test(navigator.userAgent);
            }

            // Run when page loads
            document.addEventListener("DOMContentLoaded", function () {

                const btn = document.getElementById("ndiMobileBtn");
                const title = document.getElementById("ndiModalTitle");

                if (!btn) return;

                // Hide button first
                btn.style.display = "none";

                // Show only on mobile
                if (isMobile()) {
                    btn.style.display = "block";

                    if (title) {
                        title.innerHTML = 'Login with <span class="ndi-highlight">Bhutan NDI</span> Wallet';
                    }
                }

            });

            function openNDIWallet() {

                if (!ndiDeepLinkURL) {
                    alert("NDI Wallet link not ready yet.");
                    return;
                }

                window.location.href = ndiDeepLinkURL;
            }
        </script>


    </c:when>
</c:choose>

</body>
</html>