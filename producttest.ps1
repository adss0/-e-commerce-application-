# Standard Headers
$headers = @{
    "Content-Type" = "application/json"
}
  

function New-Product {
    param (
        $apiUrl,
        $productData
    )

    $postApi = $apiUrl
    
    # Convert the hashtable to a JSON string
    $jsonBody = $productData | ConvertTo-Json
  
    # Invoke the API request using Invoke-RestMethod
    try {
        $response = Invoke-RestMethod -Uri $postApi -Method Post -Headers $headers -Body $jsonBody
        Write-Host $response
    }
    catch {
        # Handle any errors that occur
        Write-Error "Error creating product: $($_.Exception.Message)"
    }
}

function Get-product {
    param (
        $apiUrl,
        $id
    )
  
    $getApi = "$apiUrl/$id"
    $status;
  
    # Invoke the API request using Invoke-RestMethod, but for example skip the HTTP status check, and set status variable
    try {
        $response = Invoke-RestMethod -Uri $getApi -Method Get -Headers $headers -SkipHttpErrorCheck -StatusCodeVariable "status"
        Write-Host "$status $response"
    }
    catch {
        # Handle any errors that occur
        Write-Error "Error creating product: $($_.Exception.Message)"
    }
}

function Get-products {
    param (
        $apiUrl
    )

    $getApi = $apiUrl
  
    # Invoke the API request using Invoke-RestMethod
    try {
        $response = Invoke-RestMethod -Uri $getApi -Method Get -Headers $headers

        foreach($product in $response)
        {
            Write-Host $product    
        }

        Write-Host $response
    }
    catch {
        # Handle any errors that occur
        Write-Error "Error creating product: $($_.Exception.Message)"
    }
}


function Update-product {
    param (
        $apiUrl,
        $id,
        $productData

    )

        # Convert the hashtable to a JSON string
        $jsonBody = $productData | ConvertTo-Json

  
    $putApi = "$apiUrl/$id"
    $status;
  
    # Invoke the API request using Invoke-RestMethod, but for example skip the HTTP status check, and set status variable
    try {
        $response = Invoke-RestMethod -Uri $putApi -Method Put -Headers $headers -Body $jsonBody -SkipHttpErrorCheck -StatusCodeVariable "status"
        Write-Host "$status $response"
    }
    catch {
        # Handle any errors that occur
        Write-Error "Error creating product: $($_.Exception.Message)"
    }
}

function Remove-product {
    param (
        $apiUrl,
        $id
    )
  
    $deleteApi = "$apiUrl/$id"
    $status;
  
    # Invoke the API request using Invoke-RestMethod, but for example skip the HTTP status check, and set status variable
    try {
        $response = Invoke-RestMethod -Uri $deleteApi -Method Delete -Headers $headers
        Write-Host $response
    }
    catch {
        # Handle any errors that occur
        Write-Error "Error creating product: $($_.Exception.Message)"
    }
}

# Define the API endpoint URL
$apiUrl = "http://localhost:8080/products"

# Define the product data payload as a hashtable
$productData = @{
    "name"      = "productA"
}


New-product -apiUrl $apiUrl -productData $productData 
Get-product -apiUrl $apiUrl 1

$productData.name = "productB"

New-product -apiUrl $apiUrl -productData $productData 
Get-product -apiUrl $apiUrl 2

