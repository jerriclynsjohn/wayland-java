/*
 * Copyright © 2012-2013 Jason Ekstrand.
 *  
 * Permission to use, copy, modify, distribute, and sell this software and its
 * documentation for any purpose is hereby granted without fee, provided that
 * the above copyright notice appear in all copies and that both that copyright
 * notice and this permission notice appear in supporting documentation, and
 * that the name of the copyright holders not be used in advertising or
 * publicity pertaining to distribution of the software without specific,
 * written prior permission.  The copyright holders make no representations
 * about the suitability of this software for any purpose.  It is provided "as
 * is" without express or implied warranty.
 * 
 * THE COPYRIGHT HOLDERS DISCLAIM ALL WARRANTIES WITH REGARD TO THIS SOFTWARE,
 * INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS, IN NO
 * EVENT SHALL THE COPYRIGHT HOLDERS BE LIABLE FOR ANY SPECIAL, INDIRECT OR
 * CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE,
 * DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER
 * TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE
 * OF THIS SOFTWARE.
 */
package org.freedesktop.wayland.server;

import java.io.File;
import java.lang.reflect.Constructor;

import org.freedesktop.wayland.arch.Native;
import org.freedesktop.wayland.Interface;

public class Client extends NativeObjectWrapper
{
    Client(long client_ptr)
    {
        setNative(client_ptr);
    }

    public Client(Display display, int fd)
    {
        create(display, fd);
    }

    public static native Client startClient(Display display, File executable,
            String[] args, boolean clearEnvironment);

    public static Client startClient(Display display, File executable,
            String[] args)
    {
        return startClient(display, executable, args, false);
    }

    private native void setNative(long client_ptr);
    private native void create(Display display, int fd);

    public native void flush();
    public native void addDestroyListener(DestroyListener listener);
    public native Display getDisplay();
    public native void destroy();

    @Override
    protected void finalize() throws Throwable
    {
        if (isValid())
            destroy();
        super.finalize();
    }

    private static native void initializeJNI();

    static {
        Native.loadLibrary("wayland-java-util");
        Native.loadLibrary("wayland-java-server");
        initializeJNI();
    }
}

