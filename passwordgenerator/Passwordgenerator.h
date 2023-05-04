
#ifndef PASSWORDGENERATOR_PASSWORDGENERATOR_H
#define PASSWORDGENERATOR_PASSWORDGENERATOR_H

#include <QWidget>
#include <QPushButton>
#include <QSlider>
#include <QSpinBox>
#include <QLineEdit>
#include <QHBoxLayout>


class Passwordgenerator : public QWidget{

    QOBJECT_H

private:

    QPushButton *generatePassword;
    QPushButton *copyPassword;
    QPushButton *clearPasswordField;
    QSlider *slider;
    QSpinBox *spinBox;
    QLineEdit *passwordField;
    QString passwordGeneration(int lengthOfPassword);
    QHBoxLayout *layout;

public:

    Passwordgenerator(QWidget *parent);

};


#endif //PASSWORDGENERATOR_PASSWORDGENERATOR_H
