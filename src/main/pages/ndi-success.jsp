<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <!DOCTYPE html>

    <html>

    <head>

        <meta charset="UTF-8">

        <title>NDI Login Successful</title>


        <style>
            body {
                margin: 0;
                padding: 0;

                font-family: Arial, sans-serif;

                background: #f5f7f8;

                display: flex;

                justify-content: center;

                align-items: center;

                min-height: 100vh;
            }


            .container {

                width: 500px;

                background: white;

                border-radius: 20px;

                padding: 40px;

                box-shadow:
                    0 10px 30px rgba(0, 0, 0, 0.08);

                text-align: center;
            }


            .success-icon {

                width: 80px;

                height: 80px;

                border-radius: 50%;

                background: #4dc994;

                color: white;

                font-size: 45px;

                display: flex;

                justify-content: center;

                align-items: center;

                margin: 0 auto 25px auto;
            }


            h1 {

                margin-bottom: 10px;

                color: #222;
            }


            .success-message {

                color: #666;

                margin-bottom: 30px;
            }


            .user-info {

                text-align: left;

                background: #f7f9f9;

                border-radius: 12px;

                padding: 20px;

                margin-top: 20px;
            }


            .row {

                margin-bottom: 18px;
            }


            .label {

                color: #888;

                font-size: 14px;

                margin-bottom: 5px;
            }


            .value {

                color: #222;

                font-size: 18px;

                font-weight: 600;

                word-break: break-word;
            }


            .btn {

                display: inline-block;

                margin-top: 25px;

                padding: 12px 30px;

                background: #4dc994;

                color: white;

                text-decoration: none;

                border-radius: 25px;

                font-size: 16px;
            }


            .btn:hover {

                background: #3eb782;
            }
        </style>

    </head>


    <body>


        <div class="container">


            <div class="success-icon">
                ✓
            </div>


            <h1>
                NDI Login Successful
            </h1>


            <p class="success-message">
                Your identity has been successfully verified
                using Bhutan NDI Wallet.
            </p>


            <div class="user-info">


                <div class="row">

                    <div class="label">
                        ID Number
                    </div>

                    <div class="value">
                        ${idNumber}
                    </div>

                </div>


                <div class="row">

                    <div class="label">
                        Full Name
                    </div>

                    <div class="value">
                        ${fullName}
                    </div>

                </div>


                <div class="row">

                    <div class="label">
                        Relationship DID
                    </div>

                    <div class="value">
                        ${relationshipDid}
                    </div>

                </div>


                <div class="row">

                    <div class="label">
                        Thread ID
                    </div>

                    <div class="value">
                        ${threadId}
                    </div>

                </div>


            </div>


            <a href="/" class="btn">

                Test Again

            </a>


        </div>


    </body>

    </html>