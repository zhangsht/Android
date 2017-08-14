#include <jni.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <android/log.h>
#include <unistd.h>
#include <arpa/inet.h>

typedef  char byte;
#define  LOG_TAG  "android-4g"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  PACKAGESIZE 8000

int sockfd = 0;
int id = 0;
int idRemain = 0;
//const int packetHead = 0xFFEE;
//const int packageTail = 0xDDCC;
//const int HEAD = 1;
//const int TAIL = 3;
const int INDEX = 2;

void sendIndex(int id) {
    byte idToByte[4];
    memcpy(idToByte, &id, 4);
    send(sockfd, idToByte, 4, MSG_WAITALL);
}

inline bool isEqual(const int clientData, const int serverData, int type) {
    if (clientData == serverData) {
        return true;
    }
    switch(type) {
        case INDEX:
            LOGI("package index wrong at %d", id);
            break;
        default:
            break;
    }
    return false;
}

void handlePackage(const char package[PACKAGESIZE]) {
    int packageId = -1;
    memcpy(&packageId, package + 4, 4);
    if (!isEqual(id, packageId, INDEX)) {
        return;
    }
    id++;
}

void connectRemote() {
    sockfd = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (sockfd < 0) {
        LOGI("socket init error: %s",  "tcp mode");
        return;
    }
    LOGI("socket init");

    struct sockaddr_in server_addr;
    memset(&server_addr,0, sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = inet_addr("119.23.135.64");
    server_addr.sin_port = htons(443);
    if (connect(sockfd, (struct sockaddr*)&server_addr, sizeof(server_addr)) < 0) {
        LOGI("connect error: %s",  "connect server");
        return;
    }
    LOGI("socket connect");

    struct timeval timeout = {3, 0};
    if (setsockopt(sockfd, SOL_SOCKET, SO_RCVTIMEO, (const char*)&timeout, sizeof(timeout)) != 0) {
        LOGI("set Sock opt error");
        return;
    }
    LOGI("set socket timeout");
    int timeCount = 0;
    char buffer[PACKAGESIZE + 1];
    while (true) {
        if ((id + 1) % 5 == 0 && id > 0) {
            sendIndex(id + 1);
            LOGI("last 5 package data recv correctly");
        }
        int receiveLen = 0;
        int j = 0;
        int sizeLeft = PACKAGESIZE;
        while (sizeLeft > 0) {
            receiveLen = recv(sockfd, buffer + j, sizeLeft, 0);
            if (receiveLen < 0) {
                if (errno == EAGAIN) {
                    timeCount++;
                }
                break;
            }

            timeCount = 0;
            j += receiveLen;
            sizeLeft -= receiveLen;
        }

        if (receiveLen < 0) {
            if (errno == EAGAIN && timeCount < 21) {
                LOGI("socket time out, current id is %d", id);
                continue;
            }
            LOGI("socket end, current id is %d", id);
            break;
        }

        char package[PACKAGESIZE];
        memcpy(package, buffer, PACKAGESIZE);
        handlePackage(package);
    }
    LOGI("stop at : %d", id);
}

void release() {
    close(sockfd);
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_zhang_myapplication_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject obj) {
    LOGI("step connect： %s",  "call connectRemote");
    connectRemote();
    LOGI("step release： %s",  "call release");
    release();
    LOGI("step end: %s",  "socket end successfully");

    return env->NewStringUTF("socket end successfully");
}
