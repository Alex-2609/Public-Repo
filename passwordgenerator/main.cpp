#include <QApplication>
#include "Passwordgenerator.h"


int main(int argc, char *argv[]) {
    QApplication a(argc, argv);

    Passwordgenerator passwordGenerator(nullptr);
    passwordGenerator.resize(300,150);
    passwordGenerator.show();

    return QApplication::exec();
}

