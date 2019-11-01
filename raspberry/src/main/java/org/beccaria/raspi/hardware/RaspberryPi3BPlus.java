package org.beccaria.raspi.hardware;

import com.pi4j.io.gpio.*;
import com.pi4j.system.SystemInfo;
import com.pi4j.wiringpi.SoftPwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
@Named("raspi")
public class RaspberryPi3BPlus {
    public final static int VALUE_ON = 1;
    public final static int VALUE_OFF = 0;
    public final static String BOARD_MODEL = "Raspberry Pi 3 model B+";

    private Logger logger = LoggerFactory.getLogger(getClass());

    GpioPinDigitalOutput[] pins;
    GpioPinPwmOutput red_pin, green_pin, blue_pin;

    public RaspberryPi3BPlus(){
        logger.info("==========> [Raspberry Pi Board] new instance.");
        initBoard();
        if (pins != null){
            for (int i=0; i<pins.length; i++){
                logger.info("==========> [Raspberry Pi Board] GPIO available at pin: " + i + " name: " + pins[i].getName() + " state: " + pins[i].getState());
            }
        }
        logger.info("==========> [Raspberry Pi Board] pins: " + RaspiPin.GPIO_23.getAddress() + ", " + RaspiPin.GPIO_24.getAddress() + " and " + RaspiPin.GPIO_26.getAddress() + " reserved for RGB led strip.");
    }

    private void initBoard() {
        logger.info("==========> [Raspberry Pi Board] Initialize PINS of the Board...");

        try{
            GpioController gpio = GpioFactory.getInstance();

            pins = new GpioPinDigitalOutput[4];
            pins[0]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01,"pin_01", PinState.LOW);
            pins[0].setShutdownOptions(true, PinState.LOW);
            pins[1]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03,"pin_03",PinState.LOW);
            pins[1].setShutdownOptions(true, PinState.LOW);
            pins[2]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05,"pin_05", PinState.LOW);
            pins[2].setShutdownOptions(true, PinState.LOW);
            pins[3]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07,"pin_07",PinState.LOW);
            pins[3].setShutdownOptions(true, PinState.LOW);
        }catch (Error err){
            logger.error("[Raspberry Pi Board] Initialization error. May be the WiringPi native library is missing.");
        }
    }

    public String serial() {
        if (pins == null){
            return "Board not available";
        }
        try {
            return SystemInfo.getSerial().toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Board not available";
    }

    public int status(int gpio) {
        if (pins == null){
            return -1;
        }
        if (gpio >= pins.length){
            return -1;
        }
        if (pins[gpio] == null){
            return -1;
        }
        return pins[gpio].getState().getValue();
    }

    public int on(int gpio) {
        if (pins == null){
            return -1;
        }
        if (gpio >= pins.length){
            return -1;
        }
        if (pins[gpio] == null){
            return -1;
        }
        if (pins[gpio].isLow()){
            pins[gpio].high();
        }
        return pins[gpio].getState().getValue();
    }

    public int off(int gpio) {
        if (pins == null){
            return -1;
        }
        if (gpio >= pins.length){
            return -1;
        }
        if (pins[gpio] == null){
            return -1;
        }
        if (pins[gpio].isHigh()){
            pins[gpio].low();
        }
        return pins[gpio].getState().getValue();
    }

}