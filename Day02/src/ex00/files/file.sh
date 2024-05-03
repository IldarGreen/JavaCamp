#!/bin/bash


clean_em_fkn_all() {
    a=$(df -tH | grep $USER | awk {'print $4'})
    echo "Free space before clean $a"
    rm -rf ~/Library/Caches/*
    rm -rf ~/Library/Application\ Support/Code/Cache
    rm -rf ~/Library/Application\ Support/Code/CachedData
    rm -rf ~/Library/Application\ Support/Code/CachedExtension
    rm -rf ~/Library/Application\ Support/Code/CachedExtensions
    rm -rf ~/Library/Application\ Support/Code/CachedExtensionVSIXs
    rm -rf ~/Library/Application\ Support/Code/Code\ Cache
    rm -rf ~/Library/Application\ Support/Code/Service\ Worker/ScriptCache
    rm -rf ~/Library/Application\ Support/Slack/Service\ Worker/ScriptCache
    rm -rf ~/Library/Application\ Support/Slack/Service\ Worker/CacheStorage
    rm -rf ~/Library/Application\ Support/Slack/Cache
    rm -rf ~/Library/Application\ Support/Slack/Code\ Cache
    rm -rf ~/Library/Safari/Favicon\ Cache
    rm -rf ~/Library/Application\ Support/Telegram\ Desktop/tdata/user_data
    rm -rf ~/Library/Application\ Support/Google/Chrome/Default/Service\ Worker/CacheStorage
    rm -rf ~/Library/Application\ Support/Google/Chrome/Default/Service\ Worker/ScriptCache
    rm -rf ~/Library/Application\ Support/Docker\ Desktop/Cache/
    rm -rf ~/Library/Application\ Support/Docker\ Desktop/Code\ Cache
    rm -rf ~/Library/Developer/CoreSimulator/Caches
    rm -rf ~/Library/Developer/CoreSimulator/Devices
    rm -rf ~/Library/Developer/Xcode/DerivedData
    rm -rf ~/Library/Application\ Support/Code/Cache

    rm -rf ~/Library/Containers/com.apple.Safari/Data/Library/Caches
    rm -rf ~/Library/Application\ Support/zoom.us/AutoUpdater
    rm -rf ~/Library/Application\ Support/Google/Chrome/BrowserMetrics-spare.pma
    rm -rf ~/Library/Application\ Support/Google/Chrome/GrShaderCache/GPUCache
    rm -rf ~/Library/Application\ Support/Code/Service\ Worker/CacheStorage
    rm -rf ~/Library/Application\ Support/Code/Service\ Worker/ScriptCache
    rm -rf ~/Library/Application\ Support/Code/User/workspaceStorage
    rm -rf ~/Library/Application\ Support/Code/CachedExtensions
    rm -rf ~/Library/Application\ Support/Code/logs
    rm -rf ~/Library/Application\ Support/Slack/Cache
    rm -rf ~/Library/Developer/CoreSimulator/Caches
    rm -rf ~/Library/Developer/CoreSimulator/Devices
    rm -rf ~/Library/Logs
    rm -rf ~/Library/Google/GoogleSoftwareUpdate
    rm -rf ~/Library/Application\ Support/Code/CachedData
    rm -rf ~/Library/Application\ Support/Slack/logs
    rm -rf ~/Library/Application\ Support/Code/CachedExtensionVSIXs
    rm -rf ~/Library/Application\ Support/Code/Code\ Cache
    rm -rf ~/Library/Application\ Support/Google/Chrome/Default/Service\ Worker/CacheStorage
    rm -rf  ~/Library/Application\ Support/Google/Chrome/Default/Service\ Worker/ScriptCache
    rm -rf ~/Library/Application\ Support/Google/Chrome/ShaderCache/GPUCache
    rm -rf /Users/$USER/Library/Group\ Containers/6N38VWS5BX.ru.keepcoder.Telegram/stable/account-[0-9]*/postbox/media/*
    rm -rf ~/Library/Application\ Support/Slack/Code\ Cache
    rm -rf ~/Library/Application\ Support/Slack/Cache
    rm -rf ~/Library/Application\ Support/Slack/Service\ Worker/CacheStorage
    rm -rf ~/Library/Caches/*
    rm -rf ~/.Trash/*
    rm -rf ~/Library/Safari/*
    rm -rf ~/.kube/cache/*
    rm -rf ~/Library/Application\ Support/Code/CachedData/*
    rm -rf ~/Library/Application\ Support/Code/User/workspaceStoratge/*
    rm -rf ~/Library/Containers/com.apple.Safari/Data/Library/Caches/*
    rm -rf /Users/$USER/Library/Application\ Support/Slack/Crashpad/pending/*
    rm -rf /Users/$USER/Library/Application\ Support/Slack/Crashpad/completed/*
    rm -rf /Users/$USER/Library/Application\ Support/Telegram\ Desktop/tdata/emoji/*
    b=$(df -tH | grep $USER | awk {'print $4'})
    echo "Free space after clean $b"
}

clean_em_fkn_all 2> /dev/null

