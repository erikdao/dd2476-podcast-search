import { Fragment } from 'react';
import { Listbox, Transition } from '@headlessui/react';
import { CheckIcon, SelectorIcon } from '@heroicons/react/solid';
import clsx from 'clsx';
import { ICommon } from "../types";
import { ESearchType } from '../types';

interface IQueryTypeSelectProps extends ICommon {
  value: ESearchType;
  onChange(value: ESearchType): void;
}

export function QueryTypeSelect(props: IQueryTypeSelectProps) {
  return (
    <Listbox value={props.value} onChange={props.onChange}>
      {({ open }) => (
        <>
          <div className="mt-1 relative">
            <Listbox.Button className="relative w-full bg-white border border-gray-300 rounded-md shadow-sm pl-3 pr-10 py-2 text-left cursor-default focus:outline-none focus:ring-1 focus:ring-green-500 focus:border-ring-500 sm:text-sm">
              <span className="block truncate">{(props.value === ESearchType.PHRASE) ? "Phrase query" : "Multiword query"}</span>
              <span className="absolute inset-y-0 right-0 flex items-center pr-2 pointer-events-none">
                <SelectorIcon className="h-5 w-5 text-gray-400" aria-hidden="true" />
              </span>
            </Listbox.Button>

            <Transition
              show={open}
              as={Fragment}
              leave="transition ease-in duration-100"
              leaveFrom="opacity-100"
              leaveTo="opacity-0"
            >
              <Listbox.Options
                static
                className="absolute mt-1 w-full bg-white shadow-lg max-h-60 rounded-md py-1 text-base ring-1 ring-black ring-opacity-5 overflow-auto focus:outline-none sm:text-sm"
              >
                <Listbox.Option
                  key={ESearchType.PHRASE}
                  value={ESearchType.PHRASE}
                  className={({ active }) => clsx(
                    active ? 'text-white bg-spotify-green' : 'text-gray-900 bg-white',
                    "cursor-default select-none relative py-2 pl-8 pr-4"
                  )}
                >
                  {({ selected, active }) => (
                    <>
                      <span className={clsx(selected ? 'font-semibold' : 'font-normal', 'block truncate')}>Phrase query</span>
                      {selected ? (
                        <span
                          className={clsx(
                            active ? 'text-white' : 'text-spotify-green',
                            'absolute inset-y-0 left-0 flex items-center pl-1.5'
                          )}
                        >
                          <CheckIcon className="h-5 w-5" aria-hidden="true" />
                        </span>
                      ) : null}
                    </>
                  )}
                </Listbox.Option>
                <Listbox.Option
                  key={ESearchType.MULTIWORD}
                  value={ESearchType.MULTIWORD}
                  className={({ active }) => clsx(
                    active ? 'text-white bg-spotify-green' : 'text-gray-900 bg-white',
                    "cursor-default select-none relative py-2 pl-8 pr-4"
                  )}
                >
                  {({ selected, active }) => (
                    <>
                      <span className={clsx(selected ? 'font-semibold' : 'font-normal', 'block truncate')}>Multiword query</span>
                      {selected ? (
                        <span
                          className={clsx(
                            active ? 'text-white' : 'text-spotify-green',
                            'absolute inset-y-0 left-0 flex items-center pl-1.5'
                          )}
                        >
                          <CheckIcon className="h-5 w-5" aria-hidden="true" />
                        </span>
                      ) : null}
                    </>
                  )}
                </Listbox.Option>
              </Listbox.Options>
            </Transition>
          </div>
          </>
      )}
    </Listbox>
  )
}