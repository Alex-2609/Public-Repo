#include "Passwordgenerator.h"

Passwordgenerator::Passwordgenerator(QWidget *parent)
: QWidget(parent){

    generatePassword = new QPushButton(this);
    generatePassword->resize(50,20);
    generatePassword->setText("Generate");

    copyPassword = new QPushButton(this);
    copyPassword->resize(50,20);
    copyPassword->setText("Copy");
    copyPassword->move(51,0);

    clearPasswordField = new QPushButton(this);
    clearPasswordField->resize(50,20);
    clearPasswordField->setText("Clear");
    clearPasswordField->move(101,0);

    passwordField = new QLineEdit(this);
    passwordField->resize(250,20);
    passwordField->setPlaceholderText("Password");
    passwordField->setReadOnly(true);
    passwordField->move(25,40);

    slider = new QSlider(Qt::Orientation::Horizontal, this);
    slider->setMinimum(12);
    slider->setMaximum(32);
    slider->resize(50,10);

    spinBox = new QSpinBox(this);
    spinBox->setMinimum(12);
    spinBox->setMaximum(32);

    layout = new QHBoxLayout(this);
    layout->addWidget(slider);
    layout->addWidget(spinBox);

    QObject::connect(slider,&QSlider::valueChanged,spinBox,&QSpinBox::setValue);
    QObject::connect(spinBox,qOverload<int>(&QSpinBox::valueChanged),slider,&QSlider::setValue);
    QObject::connect(copyPassword,&QPushButton::pressed,[this](){passwordField->selectAll(); passwordField->copy();});
    QObject::connect(generatePassword,&QPushButton::clicked,[this](){passwordField->setText(passwordGeneration(slider->value()));});
    QObject::connect(clearPasswordField,&QPushButton::clicked,passwordField,&QLineEdit::clear);
}

QString Passwordgenerator::passwordGeneration(int lengthOfPassword) {

    QString password = "";
    srand(time(nullptr));
    int counter = 0;
    while (counter!=lengthOfPassword){

        char randomCharacter = rand() % 94 + 32;
        password += randomCharacter;
        ++counter;
    }
    return password;
}
