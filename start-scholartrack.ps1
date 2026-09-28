$ErrorActionPreference = 'Stop'

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$backendDir = Join-Path $root 'backend'
$frontendDir = Join-Path $root 'frontend'
$nodeDir = 'C:\Program Files\nodejs'
$frontendUrl = 'http://localhost:5174'

if (-not (Test-Path $nodeDir)) {
    Write-Error 'Node.js was not found at C:\Program Files\nodejs. Install Node LTS first.'
    exit 1
}

$env:Path = "$nodeDir;$env:Path"

Write-Host 'Clearing any stale local processes on the app ports...'
$ports = @(9090, 5173, 5174, 5175, 8080, 8081, 8082)
foreach ($port in $ports) {
    $connections = Get-NetTCPConnection -LocalPort $port -ErrorAction SilentlyContinue
    if ($connections) {
        foreach ($conn in $connections) {
            try {
                Stop-Process -Id $conn.OwningProcess -Force -ErrorAction SilentlyContinue
            } catch {
                # ignore cleanup failures for occupied ports
            }
        }
    }
}

Start-Sleep -Seconds 2

Write-Host 'Starting backend...'
Start-Process powershell -ArgumentList '-NoExit', '-Command', "Set-Location '$backendDir'; mvn spring-boot:run" -WorkingDirectory $root

Write-Host 'Starting frontend...'
Start-Process powershell -ArgumentList '-NoExit', '-Command', "Set-Location '$frontendDir'; $env:PATH = 'C:\Program Files\nodejs;' + $env:PATH; npm install --legacy-peer-deps; npm run dev -- --host 0.0.0.0 --port 5174" -WorkingDirectory $root

Start-Sleep -Seconds 6
try {
    Start-Process $frontendUrl
} catch {
    # ignore browser launch problems if no browser can be opened
}

Write-Host ''
Write-Host 'ScholarTrack is starting...'
Write-Host 'Backend: http://localhost:9090'
Write-Host 'Swagger: http://localhost:9090/swagger-ui.html'
Write-Host 'Frontend: http://localhost:5174'
Write-Host ''
Write-Host 'Demo login:'
Write-Host '  student / student123'
Write-Host '  verifier / verifier123'
Write-Host '  admin / admin123'
