# =====================================================================
#  test-api.ps1 - Chay thu toan bo CRUD cua school-api
#  Cach dung:  .\test-api.ps1
#  Yeu cau  :  ung dung dang chay o http://127.0.0.1:8080
# =====================================================================

$BASE = 'http://127.0.0.1:8080/api/students'

function Call-Api {
    param(
        [string]$Method,
        [string]$Url,
        [string]$Body,
        [string]$Title
    )
    Write-Host ""
    Write-Host "--- $Title" -ForegroundColor Cyan
    Write-Host "    $Method $Url" -ForegroundColor DarkGray

    $params = @{
        Method      = $Method
        Uri         = $Url
        UseBasicParsing = $true
        TimeoutSec  = 20
    }
    if ($Body) {
        $params.Body        = [Text.Encoding]::UTF8.GetBytes($Body)
        $params.ContentType = 'application/json; charset=utf-8'
    }

    try {
        $res = Invoke-WebRequest @params
        Write-Host "    HTTP $([int]$res.StatusCode)" -ForegroundColor Green
        if ($res.Content) { Write-Host "    $($res.Content)" }
    }
    catch {
        $code = [int]$_.Exception.Response.StatusCode
        $reader = New-Object IO.StreamReader($_.Exception.Response.GetResponseStream())
        Write-Host "    HTTP $code" -ForegroundColor Yellow
        Write-Host "    $($reader.ReadToEnd())"
    }
}

Write-Host "=====================================================" -ForegroundColor White
Write-Host " CHAY THU CRUD - school-api" -ForegroundColor White
Write-Host "=====================================================" -ForegroundColor White

# 1. POST - tao moi
Call-Api POST $BASE '{"department":"Cong nghe thong tin","studentName":"Nguyen Van An"}' 'POST  - Tao sinh vien moi'
Call-Api POST $BASE '{"department":"Kinh te","studentName":"Tran Thi Binh"}'             'POST  - Tao sinh vien thu hai'

# 2. GET - doc
Call-Api GET  $BASE                          $null 'GET   - Danh sach (chi ban ghi active)'
Call-Api GET  "$BASE/1"                      $null 'GET   - Chi tiet theo id'

# 3. PUT - cap nhat
Call-Api PUT  "$BASE/1" '{"department":"Khoa hoc may tinh","studentName":"Nguyen Van An"}' 'PUT   - Cap nhat id=1'

# 4. DELETE - xoa mem
Call-Api DELETE "$BASE/2"                    $null 'DELETE- Xoa mem id=2'
Call-Api GET    "$BASE/2"                    $null 'GET   - id=2 sau khi xoa (mong doi 404)'
Call-Api GET    $BASE                        $null 'GET   - Danh sach (id=2 da bien mat)'
Call-Api GET    "$BASE`?includeInactive=true" $null 'GET   - Ke ca ban ghi da xoa'

# 5. PATCH - khoi phuc
Call-Api PATCH "$BASE/2/restore"             $null 'PATCH - Khoi phuc id=2'

# 6. Validation
Call-Api POST $BASE '{"department":"","studentName":""}' 'POST  - Du lieu rong (mong doi 400)'

Write-Host ""
Write-Host "===================== XONG =====================" -ForegroundColor White
