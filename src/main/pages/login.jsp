<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>NDI Login</title>

    <style>

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            min-height: 100vh;

            display: flex;
            justify-content: center;
            align-items: center;

            font-family: Arial, sans-serif;

            background: #f5f5f5;
        }

        .ndi-container {

            width: 590px;

            background: #ffffff;

            border-radius: 28px;

            padding: 40px 30px;

            text-align: center;

            box-shadow:
                0 10px 30px rgba(0, 0, 0, 0.08);
        }

        .title {

            font-size: 20px;

            font-weight: 700;

            margin-bottom: 30px;

            color: #333;
        }

        .ndi-highlight {
            color: #5AC994;
        }

        .qr-wrapper {

            width: 250px;
            height: 250px;

            margin: 0 auto 30px auto;

            padding: 8px;

            border: 3px solid #57C08A;

            border-radius: 15px;

            background: white;

            display: flex;

            align-items: center;

            justify-content: center;
        }

        #qrcode {
            width: 230px;
            height: 230px;
        }

        .instructions {

            text-align: left;

            max-width: 350px;

            margin: 0 auto 30px auto;

            color: #999;

            font-size: 16px;

            line-height: 1.5;
        }

        .instructions li {
            margin-bottom: 10px;
        }

        .support {

            margin-top: 30px;

            color: #5AC994;

            font-weight: 700;
        }

    </style>

</head>

<script src="https://cdn.jsdelivr.net/npm/qrcode/build/qrcode.min.js"></script>

<script>
    window.onload = function () {

        const qrUrl = "${qrUrl}";;

        QRCode.toString(
            qrUrl,
            {
                type: "svg",
                errorCorrectionLevel: "H",
                margin: 2,
                width: 230
            },
            function (error, svgData) {

                if (error) {
                    console.error("QR generation failed:", error);
                    return;
                }

                document.getElementById("qrcode").innerHTML = svgData;
            }
        );
    };
</script>
<body>


<div class="ndi-container">


    <div class="title">

        Scan with
        <span class="ndi-highlight">
            Bhutan NDI
        </span>
        Wallet

    </div>


    <!-- QR CODE -->

    <div class="qr-wrapper">

        <div id="qrcode">
            QR CODE
        </div>

    </div>


    <!-- Instructions -->

    <ol class="instructions">

        <li>
            Open Bhutan NDI Wallet on your phone
        </li>

        <li>
            Tap the Scan button and scan the QR code
        </li>

    </ol>


    <div class="support">

        Get Support

    </div>


</div>


</body>

</html>