<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Uploading Files Example with Spring Boot, Freemarker</title>
</head>

<body onload="updateSize();">
<form name="uploadingForm" enctype="multipart/form-data" action="/" method="POST">
    <!--<p>
        Folder Name : <input type="text" id="folder" name="folder" required>

    </p>-->
    <table>
        <tr>
            <td>Select folder :</td>
            <td>
                <select name="prefix" id="prefix" onchange="getPrefix();" required>
                    <option value="banners">Banners</option>
                    <option value="mobile-recipes">Recipes</option>
                    <option value="charities">Charities</option>
                    <option value="categories">Categories</option>
                    <option value="articles">Articles</option>
                    <option value="products">Products</option>
                    <option value="mobile/smartswitchoffers">Mobile-Smartswitch</option>
                    <option value="OS_Marketing/SwitchForRewards/images">SwitchForRewards</option>
                    <option value="bill-payment-vendors">BillPaymentsVendors</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>

                Choose files to be uploaded :
            </td>
            <td>
                <p>
                    <input id="fileInput" type="file" name="uploadingFiles"
                           onchange="updateSize();" multiple required>
                    selected files: <span id="fileNum">0</span>;
                    total size: <span id="fileSize">0</span>
                </p>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <p>
                    <input type="submit" value="Upload files" size="20">
                </p>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="color:red">
                <span id="error">${error}</span>
            </td>
        </tr>
    </table>
</form>
<div>
    <div>Uploaded files to <span id="folder"></span>. You can test by clicking below Image(s)</div>
    <#list files as file>
    <div>
        <a href="https://www-qa.pnp.co.za/${prefix}/${file.getName()}" target="_blank">${file.getName()}</a>
    </div>
    </#list>
</div>
<script>
    function getPrefix() {
        var prefix = document.getElementById("folder");
        prefix.innerHTML = document.getElementById("prefix").value;
    }

    function updateSize() {
        var prefix = document.getElementById("folder");
        prefix.innerHTML = document.getElementById("prefix").value;
        var error_text = document.getElementById("error");

        var nBytes = 0,
                oFiles = document.getElementById("fileInput").files,
                nFiles = oFiles.length;
        for (var nFileId = 0; nFileId < nFiles; nFileId++) {
            nBytes += oFiles[nFileId].size;
        }

        var sOutput = nBytes + " bytes";
        // optional code for multiples approximation
        for (var aMultiples = ["KiB", "MiB", "GiB", "TiB", "PiB", "EiB", "ZiB", "YiB"], nMultiple = 0, nApprox = nBytes / 1024; nApprox > 1; nApprox /= 1024, nMultiple++) {
            sOutput = nApprox.toFixed(3) + " " + aMultiples[nMultiple] + " (" + nBytes + " bytes)";
        }
        // end of optional code

        document.getElementById("fileNum").innerHTML = nFiles;
        document.getElementById("fileSize").innerHTML = sOutput;
    }


</script>
</body>
</html>